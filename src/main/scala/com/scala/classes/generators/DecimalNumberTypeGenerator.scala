/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import scala.collection.mutable.ArrayBuffer

object DecimalNumberTypeGenerator extends Generator {

  /**
    * this method generates a random RandomDouble
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRandomDecimalNumber(format: String, qualifiers:ArrayBuffer[String]):String = {
    var signDigits:Int = 0
    var length:Int = 10
    var min:Double = 0
    var max:Double = 0
    var roundingType:String = null
    var nullPercentage:Double = 0.0
    val splitLists:Tuple2[Array[String],Array[String]] = filterQualifiers("RandomDouble", format)
    val formatsThatNeedQualifierChecks:Array[String] = splitLists._1
    val formatsNotNeedingQualifiers = splitLists._2
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "length" => length = qualifiers(i).toInt
        case "signDigits" => signDigits = qualifiers(i).toInt
        case "min" => min = qualifiers(i).toDouble
        case "max" => max = qualifiers(i).toDouble
        case "nullable" => nullPercentage = qualifiers(i).toDouble
      }
    }
    for (i <- 0 until formatsNotNeedingQualifiers.length) {
      formatsNotNeedingQualifiers(i) match {
        case "roundup" => roundingType = "roundup"
        case "rounddown" => roundingType = "rounddown"
        case "roundhalf" => roundingType = "roundhalf"
      }
    }
    val result = padNumberWithZeros(randomDouble(min,max,signDigits,roundingType).toString,length)
    val randomNum = randomDouble(0,1,2,"rounddown")
    if(randomNum<nullPercentage) "" else result
  }
}
