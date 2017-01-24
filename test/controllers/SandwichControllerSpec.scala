package controllers

import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Future

class SandwichControllerSpec extends PlaySpec with OneAppPerSuite {

  val controller = HomePageController

  "SandwichController" should {

    "render a page" when {
      "we try to hit the route /sandwiches" in {
        val result: Option[Future[Result]] = route(app, FakeRequest(GET, "/sandwiches"))
        result.map(status(_)) mustBe Some(OK)
        val text: String = result.map(contentAsString(_)).get
      }
    }
  }
}
