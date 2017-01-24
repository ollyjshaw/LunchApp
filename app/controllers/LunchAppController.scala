package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{Action, Controller}

@Singleton
class LunchAppController @Inject() extends Controller{

  def home = Action {
    Ok(views.html.index("Your new application is ready."))
  }

}
