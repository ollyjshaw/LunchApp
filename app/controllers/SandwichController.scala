package controllers

import play.api.Play
import play.api.mvc.{Action, Controller}
import services.{ConnectedSandwichService, SandwichService}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import uk.gov.hmrc.play.http.Upstream5xxResponse

trait SandwichController extends Controller {

  def sandwichService : SandwichService

  //see https://www.playframework.com/documentation/2.5.x/ScalaAsync
  def sandwiches = Action.async {
    val availableSarnies = sandwichService.allSandwiches
    availableSarnies.map( list => {
      Ok(views.html.sandwiches(list))
    }).recover{
      case error5xx: Upstream5xxResponse => {
        Ok(views.html.error())
      }
    }
  }
}

object SandwichController extends SandwichController {
  override def sandwichService: SandwichService = {
    if (play.api.Play.isTest(play.api.Play.current)) {
      SandwichService
    }
    else ConnectedSandwichService
  }
}
