package controllers



import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.test.FakeRequest
import play.api.test.Helpers._

class HomePageControllerSpec  extends PlaySpec with OneAppPerSuite{

  //a fake for illustration
  object FakeMorningGreeter extends  TimeGreetingService {
    def greeting = "Morning"
  }

  //a fake for illustration
  object FakeAfternoonGreeter extends TimeGreetingService {
    def greeting = "Afternoon"
  }

  //can we somehow inject here?
  object TestController extends HomePageController
  val controller = TestController


  "HomePageController"  should {

    "say morning " when {
      val result = controller.land()(FakeRequest(GET, "foo"))
      status(result) mustBe OK
      contentAsString(result) must include ("Lunch?")
      contentAsString(result) must include ("Morning")
    }

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





}