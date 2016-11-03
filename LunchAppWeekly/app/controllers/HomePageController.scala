package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{Action, Controller}

object TimeGreetingService {
  def land = ()
}

@Singleton
class HomePageController @Inject() extends Controller{

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def landing = Action {
    Ok(views.html.landing("Hello"))
  }

}
