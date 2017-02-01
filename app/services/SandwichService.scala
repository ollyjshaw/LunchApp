package services

import models.Sandwich

/**
  * Created by ollyjshaw on 01/02/17.
  */
trait SandwichService {
   def allSandwiches : List[Sandwich]
}

object SandwichService extends SandwichService {
  //TODO
  override def allSandwiches: List[Sandwich] = List()
}
