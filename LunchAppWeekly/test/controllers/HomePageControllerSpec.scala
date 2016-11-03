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

    "say morning " ignore {
      val result = controller.land()(FakeRequest(GET, "foo"))
      status(result) mustBe OK
      contentAsString(result) must include("Lunch?")
      contentAsString(result) must include("Morning")
    }

    "say afternoon " in  {
      val result = controller.land()(FakeRequest(GET, "foo"))
      status(result) mustBe OK
      contentAsString(result) must include("Lunch?")
      contentAsString(result) must include("Afternoon")
    }

    "have some content " ignore {
      val result = controller.land()(FakeRequest(GET, "foo"))
      status(result) mustBe OK
      contentAsString(result) must include ("Morning want to order Lunch?")
    }

    "Say hello" when {
      "I go to the landing page" in {
        val result = route(app, FakeRequest(GET, "/landing"))
        result.map(contentAsString(_)).get must include ("want to order Lunch?")
      }
    }



    "not return 404" when {
      "I go to the route /landing" in {
        val result = route(app, FakeRequest(GET, "/landing"))
        status(result.get) must not be(NOT_FOUND)
      }
    }
  }




}