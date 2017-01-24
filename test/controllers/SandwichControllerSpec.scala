package controllers

import models.Sandwich
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.SandwichService

import scala.concurrent.Future

object NoSandwichService extends SandwichService {
  override def availableSandwiches(): List[Sandwich] = List()
}

object SomeSandwichService extends SandwichService {
  override def availableSandwiches(): List[Sandwich] = List(Sandwich("foo", "bar", 99.99))
}

object OneSandwichService extends SandwichService {
  val sandwich = Sandwich("Peas Pudding", "Tasty Classic", 1.99)
  override def availableSandwiches(): List[Sandwich] = List(sandwich)
}


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

    "show a no sandwiches available when there are none " in {
      val noSandwichService = NoSandwichService
      val noSarnieController = new SandwichController {
        val sandwichService = noSandwichService
      }
      val result = noSarnieController.sandwiches()(FakeRequest(GET, "foo"))
      status(result) mustBe OK
      contentAsString(result) must include("Are you hungry?")
      contentAsString(result) must include("Sorry, we don't have any sandwiches")
    }

    "show a choose sandwich message when there are some sandwiches " in {
      val someSandwichService = SomeSandwichService
      val someSarnieController = new SandwichController {
        val sandwichService = someSandwichService
      }
      val result = someSarnieController.sandwiches()(FakeRequest(GET, "foo"))
      status(result) mustBe OK
      contentAsString(result) must include("Are you hungry?")
      contentAsString(result) must include("Please choose a sandwich")
      contentAsString(result) must not include("Sorry, we don't have any sandwiches")
    }

    "show a sandwich description and price when there is one sandwich " in {
      val oneSandwichService = OneSandwichService
      val oneSarnieController = new SandwichController {
        val sandwichService = oneSandwichService
      }
      val result = oneSarnieController.sandwiches()(FakeRequest(GET, "foo"))
      status(result) mustBe OK
      contentAsString(result) must include("Are you hungry?")
      contentAsString(result) must include("Please choose a sandwich")
      contentAsString(result) must not include("Sorry, we don't have any sandwiches")
      contentAsString(result) must include("Peas Pudding")
      contentAsString(result) must include("Tasty Classic")
      contentAsString(result) must include("£1.99")
    }

    "show a three sandwich descriptions and prices when there are three sandwichs " in {

      val sandwich1 = Sandwich("Peas Pudding", "Tasty Classic", 1.99)
      val sandwich2 = Sandwich("Ham", "Finest Norfolk Ham", 1.22)
      val sandwich3 = Sandwich("Cheese", "With one grape", 99.99)
      val sarnies = List(sandwich1,sandwich2,sandwich3)

      val multiSandwichService = new MultiSandwichService (sarnies)
      val multiSarnieController = new SandwichController {
        val sandwichService = multiSandwichService
      }
      val result = multiSarnieController.sandwiches()(FakeRequest(GET, "foo"))
      status(result) mustBe OK
      contentAsString(result) must include("Are you hungry?")
      contentAsString(result) must include("Please choose a sandwich")
      contentAsString(result) must not include("Sorry, we don't have any sandwiches")

      for (s <- sarnies){
        contentAsString(result) must include(s.name)
        contentAsString(result) must include(s.description)
        contentAsString(result) must include("£"+s.price)
      }
    }
  }
}

class MultiSandwichService(sandwiches: List[Sandwich]) extends SandwichService {
  override def availableSandwiches(): List[Sandwich] = sandwiches
}

