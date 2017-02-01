package controllers

import models.Sandwich
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.SandwichService

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

  }
}

object NoSandwichService extends SandwichService {
  override def allSandwiches: List[Sandwich] = List()
}

object OneSandwichService extends SandwichService {
  val sandwich = new Sandwich("Ham", "Porky", 1.55)
  override def allSandwiches: List[Sandwich] = List(sandwich)
}

object TwoSandwichService extends SandwichService {
  val sandwich1 = new Sandwich("Cheese", "Cheesy", 1.99)
  val sandwich2 = new Sandwich("Ham", "Porky", 1.55)

  override def allSandwiches: List[Sandwich] = List(sandwich1,sandwich2)
}
