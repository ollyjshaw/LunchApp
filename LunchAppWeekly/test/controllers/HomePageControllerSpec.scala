package controllers



import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.test.FakeRequest
import play.api.test.Helpers._

class HomePageControllerSpec  extends PlaySpec with OneAppPerSuite{


  "HomePageController"  should {
    "not return 404" when {
      "I go to the route /landing" in {
        val result = route(app, FakeRequest(GET, "/landing"))
        status(result.get) must not be(NOT_FOUND)
      }
    }
  }


  "render the correct page with the expected text " when {
    "I go to the homepage" in {
      val result = controller.landing()(FakeRequest(GET, "/landing"))
      status(result) mustBe OK
      contentAsString(result) must include("Hello")

    }
  }


  object TestController extends HomePageController
  val controller = TestController
}