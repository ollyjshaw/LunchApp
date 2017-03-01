package models

import play.api.libs.json.{Format, Json}

case class Sandwich(name: String, description: String, price: BigDecimal)

object Sandwich {
  implicit val formats: Format[Sandwich] = Json.format[Sandwich]
}
