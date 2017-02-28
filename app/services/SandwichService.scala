package services

import models.Sandwich
import uk.gov.hmrc.play.http._
import uk.gov.hmrc.play.http.ws.WSGet

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

trait SandwichService {
   def allSandwiches : Future[List[Sandwich]]
}

object ConnectedSandwichService extends SandwichService {

  val url = "http://localhost:3000/sandwiches"

  val http = new WSGet {
    override val hooks = NoneRequired
  }

  override def allSandwiches: Future[List[Sandwich]] = {
    implicit val hc = HeaderCarrier()

    http.GET[List[Sandwich]](url)
  }

}

object SandwichService extends SandwichService {
  //TODO, this is a pretty basic implementation!
  import play.api.libs.concurrent.Execution.Implicits.defaultContext
  override def allSandwiches: Future[List[Sandwich]] = Future(List(

    Sandwich(name = "Ham", description = "Ham!", price = 5.62)

  ))
}
