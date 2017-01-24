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
  }
}
