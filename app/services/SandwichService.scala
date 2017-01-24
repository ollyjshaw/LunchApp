package services

import models.Sandwich

trait SandwichService {
  def availableSandwiches(): List[Sandwich]
}

object SandwichService extends SandwichService {
  override def availableSandwiches(): List[Sandwich] = List()
}
