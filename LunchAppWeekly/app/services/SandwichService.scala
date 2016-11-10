package services

import models.Sandwich


trait SandwichService {
  def getSandwiches(): List[Sandwich]
}

object SandwichService extends SandwichService {
  override def getSandwiches(): List[Sandwich] = {
    List()
  }
}
