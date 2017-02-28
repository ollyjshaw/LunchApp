package controllers

import play.api.mvc.{Action, Controller}
import services.{ConnectedSandwichService, SandwichService}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

trait SandwichController extends Controller {

  def sandwichService : SandwichService

  def sandwiches = Action.async {
    val availableSarnies = sandwichService.allSandwiches
    availableSarnies.map( list => {
      Ok(views.html.sandwiches(list))
    })
  }
}

object SandwichController extends SandwichController {
  override def sandwichService: SandwichService = ConnectedSandwichService
}
