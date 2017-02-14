package services

import models.Sandwich

trait SandwichService {
   def allSandwiches : List[Sandwich]
}

object SandwichService extends SandwichService {
  //TODO, this is a pretty basic implementation!
  override def allSandwiches: List[Sandwich] = List(

    Sandwich(name = "Ham", description = "Ham!", price = 5.62)

  )
}
