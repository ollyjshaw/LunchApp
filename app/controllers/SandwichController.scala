package controllers

import play.api.mvc.{Action, Controller}
import services.SandwichService

trait SandwichController extends Controller {

  def sandwichService : SandwichService

  def sandwiches = Action {
    val availableSarnies = sandwichService.allSandwiches
    Ok(views.html.sandwiches(availableSarnies))
  }
}

object SandwichController extends SandwichController {
  override def sandwichService: SandwichService = SandwichService
}
