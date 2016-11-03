package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{Action, Controller}
import org.joda.time.DateTime

trait TimeGreetingService {
  def greeting : String
}

object RealTimeGreeterService extends TimeGreetingService {
  def greeting = {
    if (DateTime.now.hourOfDay().get < 12) {
      "Morning"
    } else {
      "Afternoon"
    }
  }
}

@Singleton
class HomePageController @Inject() extends Controller {

  val greeter = RealTimeGreeterService

  def land = Action {
    Ok(views.html.landing(greeter.greeting))
  }
}
