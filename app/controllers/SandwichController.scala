package controllers

import play.api.mvc.{Action, Controller}

object SandwichController extends Controller {

  def sandwiches = Action {
    Ok(views.html.sandwiches())
  }
}
