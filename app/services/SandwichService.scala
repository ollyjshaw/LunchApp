package services

import models.Sandwich

trait SandwichService {
  def availableSandwiches(): List[Sandwich]
}

object InterimSandwichService extends SandwichService {

  val sandwich1 = Sandwich("Peas Pudding", "Tasty Classic", 1.99)
  val sandwich2 = Sandwich("Ham", "Finest Norfolk Ham", 1.22)
  val sandwich3 = Sandwich("Cheese", "With one grape", 99.99)
  val sarnies = List(sandwich1,sandwich2,sandwich3)

  override def availableSandwiches(): List[Sandwich] = sarnies
}
