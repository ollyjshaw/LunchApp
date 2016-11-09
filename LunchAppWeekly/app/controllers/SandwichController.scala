package controllers

import play.api.mvc.{Action, Controller}
import services.SandwichService


trait SandwichController extends Controller {

  def sandwichService: SandwichService

  def getSandwiches = Action {
    Ok(views.html.sandwiches())
  }
}

object SandwichController extends SandwichController {
  val sandwichService: SandwichService = SandwichService
}
