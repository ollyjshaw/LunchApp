
package controllers

import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Future

class LunchAppControllerSpec extends PlaySpec with OneAppPerSuite{

  "LunchAppController" should {
      "not return 404" when {
        "we try to hit the route /home" in {
          route(app, FakeRequest(GET, "/home")).map(status(_)) must not be Some(NOT_FOUND)
        }
      }
    }

    "render a page" when {
      "we try to hit the route /home" in {
        val result: Option[Future[Result]] = route(app, FakeRequest(GET, "/home"))
        result.map(status(_)) mustBe Some(OK)
        val text: String = result.map(contentAsString(_)).get

      }
    }
}
