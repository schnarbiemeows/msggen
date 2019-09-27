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
object MoneyTypeGenerator {

  /**
    * this method generates a random EnumMoney
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeEnumMoney(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

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
    var roundingType:String = null
    val splitLists:Tuple2[Array[String],Array[String]] = filterQualifiers("RandomMoney", format)
    val formatsThatNeedQualifierChecks:Array[String] = splitLists._1
    val formatsNotNeedingQualifiers = splitLists._2
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "min" => min = qualifiers(i).toFloat
        case "max" => max = qualifiers(i).toFloat
      }
    }
    for (i <- 0 until formatsNotNeedingQualifiers.length) {
      formatsNotNeedingQualifiers(i) match {
        case "roundup" => roundingType = "roundup"
        case "rounddown" => roundingType = "rounddown"
        case "roundhalf" => roundingType = "roundhalf"
        case _ => None
      }
    }
    padMoneyWithZeros(randomFloat(min,max,signDigits,roundingType).toString)
  }

  /**
    * this method generates a random ExternalMoney
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeExternalMoney(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }
}
