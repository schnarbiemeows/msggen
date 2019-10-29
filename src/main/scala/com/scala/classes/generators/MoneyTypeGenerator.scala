/*
 * Created 2019 by Dylan Kessler
 */

/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different Money types
  */
object MoneyTypeGenerator extends Generator {

  /**
    * this method generates a random RandomMoney
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRandomMoney(format: String,qualifiers:ArrayBuffer[String]):String = {
    var signDigits:Int = 2
    var min:Float = 0
    var max:Float = 0
    var roundingType:Option[String] = None
    var nullPercentage:Double = 0.0
    val splitLists:Tuple2[Array[String],Array[String]] = filterQualifiers("RandomMoney", format)
    val formatsThatNeedQualifierChecks:Array[String] = splitLists._1
    val formatsNotNeedingQualifiers = splitLists._2
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "min" => min = qualifiers(i).toFloat
        case "max" => max = qualifiers(i).toFloat
        case "nullable" => nullPercentage = qualifiers(i).toDouble
      }
    }
    for (i <- 0 until formatsNotNeedingQualifiers.length) {
      formatsNotNeedingQualifiers(i) match {
        case "roundup" => roundingType = Some("roundup")
        case "rounddown" => roundingType = Some("rounddown")
        case "roundhalf" => roundingType = Some("roundhalf")
        case _ => None
      }
    }
    val result = padMoneyWithZeros(randomFloat(min,max,signDigits,roundingType.getOrElse("roundhalf")).toString)
    val randomNum = randomDouble(0,1,2,"rounddown")
    if(randomNum<nullPercentage) "" else result
  }
}
