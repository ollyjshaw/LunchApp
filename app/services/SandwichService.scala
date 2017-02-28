package services

import models.Sandwich
import uk.gov.hmrc.play.http._
import uk.gov.hmrc.play.http.ws.WSGet

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

trait SandwichService {
   def allSandwiches : List[Sandwich]
}

object ConnectedSandwichService extends SandwichService {

  val url = "http://localhost:3000/sandwiches"

  val http = new WSGet {
    override val hooks = NoneRequired
  }

  override def allSandwiches: List[Sandwich] = {
    implicit val hc = HeaderCarrier()

    val future = http.GET[List[Sandwich]](url)
    Await.result(future, 5 seconds)
  }

}

object SandwichService extends SandwichService {
  //TODO, this is a pretty basic implementation!
  override def allSandwiches: List[Sandwich] = List(

    Sandwich(name = "Ham", description = "Ham!", price = 5.62)

  )
}
