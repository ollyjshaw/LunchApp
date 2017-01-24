package controllers

import play.api.mvc.{Action, Controller}
import services.{InterimSandwichService, SandwichService}

trait SandwichController extends Controller {

  def sandwichService : SandwichService

  def sandwiches = Action {
    Ok(views.html.sandwiches(sandwichService.availableSandwiches()))
  }
}

object SandwichController extends SandwichController {
  val sandwichService = InterimSandwichService
}
