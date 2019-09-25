/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import scala.collection.mutable.ArrayBuffer

object FloatTypeGenerator {

  /**
    * this method generates a random EnumFloat
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeEnumFloat(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * this method generates a random RandomFloat
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRandomFloat(format: String,qualifiers:ArrayBuffer[String]):String = {
    var signDigits:Int = 0
    var length:Int = 0
    var min:Float = 0
    var max:Float = 0
    var roundingType:String = null
    val splitLists:Tuple2[Array[String],Array[String]] = filterQualifiers("RandomFloat", format)
    val formatsThatNeedQualifierChecks:Array[String] = splitLists._1
    val formatsNotNeedingQualifiers = splitLists._2
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "length" => length = qualifiers(i).toInt
        case "signDigits" => signDigits = qualifiers(i).toInt
        case "min" => min = qualifiers(i).toFloat
        case "max" => max = qualifiers(i).toFloat
      }
    }
    for (i <- 0 until formatsNotNeedingQualifiers.length) {
      formatsNotNeedingQualifiers(i) match {
        case "roundup" => roundingType = "roundup"
        case "rounddown" => roundingType = "rounddown"
        case "roundhalf" => roundingType = "roundhalf"
      }
    }
    padNumberWithZeros(randomFloat(min,max,signDigits,roundingType).toString,length)
  }

  /**
    * this method generates a random ExternalFloat
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeExternalFloat(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * this method generates a random RangedFloat
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRangedFloat(format: String,qualifiers:ArrayBuffer[String]):String = {
    var signDigits:Int = 0
    var length:Int = 0
    var min:Float = 0
    var max:Float = 0
    var roundingType:String = null
    val splitLists:Tuple2[Array[String],Array[String]] = filterQualifiers("RangedFloat", format)
    val formatsThatNeedQualifierChecks:Array[String] = splitLists._1
    val formatsNotNeedingQualifiers = splitLists._2
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "length" => length = qualifiers(i).toInt
        case "signDigits" => signDigits = qualifiers(i).toInt
        case "min" => min = qualifiers(i).toFloat
        case "max" => max = qualifiers(i).toFloat
      }
    }
    for (i <- 0 until formatsNotNeedingQualifiers.length) {
      formatsNotNeedingQualifiers(i) match {
        case "roundup" => roundingType = "roundup"
        case "rounddown" => roundingType = "rounddown"
        case "roundhalf" => roundingType = "roundhalf"
      }
    }
    padNumberWithZeros(randomFloat(min,max,signDigits,roundingType).toString,length)
  }
}
