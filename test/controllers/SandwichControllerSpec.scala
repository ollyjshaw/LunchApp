package controllers

import models.Sandwich
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.SandwichService
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import uk.gov.hmrc.play.http.Upstream5xxResponse

import scala.concurrent.Future

class SandwichControllerSpec extends PlaySpec with OneAppPerSuite {

  "SandwichController" should {

    "render a page" when {
      "we try to hit the route /sandwiches" in {
        val result: Option[Future[Result]] = route(app, FakeRequest(GET, "/sandwiches"))
        result.map(status(_)) mustBe Some(OK)
        val text: String = result.map(contentAsString(_)).get
      }
    }

    "show a title " in {
      val controller = SandwichController
      val result = controller.sandwiches()(FakeRequest(GET, "foo"))
      status(result) mustBe OK
      contentAsString(result) must include("Are you hungry?")
    }

    "Display a no sandwich message when there are no sandwiches" in {

      val noSandwichService = NoSandwichService

      val controller = new SandwichController {
        val sandwichService = noSandwichService
      }

      val result = controller.sandwiches()(FakeRequest(GET, "foo"))
      status(result) mustBe OK

      contentAsString(result) must include ("Are you hungry?")
      contentAsString(result) must include ("Sorry, we're sold out")
    }

    "Display name, description and price of a sandwich when 1 is available" in {

      val oneSandwichService = OneSandwichService

      val sarnie = Sandwich("foo", "bar", 1.56)
      val inList = List(sarnie)
      val controller = new SandwichController {
        val sandwichService = oneSandwichService
      }

      val result = controller.sandwiches()(FakeRequest(GET, "foo"))
      status(result) mustBe OK

      contentAsString(result) must include ("Are you hungry?")
      contentAsString(result) must not include ("Sorry, we're sold out")
      contentAsString(result) must include ("Please choose a sandwich")

      contentAsString(result) must include ("Ham")
      contentAsString(result) must include ("Porky")
      contentAsString(result) must include ("£1.55")

    }

    "Display name, description and price of two sandwich when two available" in {

      val twoSandwichService = TwoSandwichService
      val controller = new SandwichController {
        val sandwichService = twoSandwichService
      }

      val result = controller.sandwiches()(FakeRequest(GET, "foo"))
      status(result) mustBe OK

      contentAsString(result) must include ("Are you hungry?")
      contentAsString(result) must not include ("Sorry, we're sold out")
      contentAsString(result) must include ("Please choose a sandwich")

      contentAsString(result) must include ("Ham")
      contentAsString(result) must include ("Porky")
      contentAsString(result) must include ("£1.55")

      contentAsString(result) must include ("Cheese")
      contentAsString(result) must include ("Cheesy")
      contentAsString(result) must include ("£1.99")

    }

    "Display a nice error message when the server returns 5xx" in {

      val erroringService = ErrorSandwichService
      val controller = new SandwichController {
        val sandwichService = erroringService
      }

      val result = controller.sandwiches()(FakeRequest(GET, "foo"))
      status(result) mustBe OK

      contentAsString(result) must include ("Sorry there was an error")

    }

  }
}

object NoSandwichService extends SandwichService {
  override def allSandwiches: Future[List[Sandwich]] = Future(List())
}

object OneSandwichService extends SandwichService {
  val sandwich = new Sandwich("Ham", "Porky", 1.55)
  override def allSandwiches: Future[List[Sandwich]] = Future(List(sandwich))
}

object TwoSandwichService extends SandwichService {
  val sandwich1 = new Sandwich("Cheese", "Cheesy", 1.99)
  val sandwich2 = new Sandwich("Ham", "Porky", 1.55)

  override def allSandwiches: Future[List[Sandwich]] = Future(List(sandwich1,sandwich2))
}

object ErrorSandwichService extends SandwichService {
  override def allSandwiches: Future[List[Sandwich]] = return Future.failed( new Upstream5xxResponse("oops", 500, 500) )
}
