package services

import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}


class SandwichServiceSpec extends PlaySpec with OneAppPerSuite {

  "SandwichService" should {
    "return a list of sandwiches" in {
      val sandwichService = SandwichService
      val result = sandwichService.getSandwiches()
      result must be(List())
    }
  }

}
