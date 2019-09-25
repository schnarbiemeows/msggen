/*
 * Created 2019 by Dylan Kessler
 */

/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different Int types
  */
object IntTypeGenerator {

  /**
    * this method generates a random EnumInt
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeEnumInt(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * this method generates a random RandomInt
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRandomInt(format: String,qualifiers:ArrayBuffer[String]):String = {
    var length:Int = 0
    var min:Int = 0
    var max:Int = 0
    val splitLists:Tuple2[Array[String],Array[String]] = filterQualifiers("RandomInt", format)
    val formatsThatNeedQualifierChecks:Array[String] = splitLists._1
    //val formatsNotNeedingQualifiers = splitLists._2
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "length" => length = qualifiers(i).toInt
        case "min" => min = qualifiers(i).toInt
        case "max" => max = qualifiers(i).toInt
      }
    }
    padNumberWithZeros(randomInteger(min,max).toString,length)
  }

  /**
    * this method generates a random ExternalInt
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeExternalInt(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * this method generates a random RangedInt
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRangedInt(format: String,qualifiers:ArrayBuffer[String]):String = {
    var length:Int = 0
    var min:Int = 0
    var max:Int = 0
    val splitLists:Tuple2[Array[String],Array[String]] = filterQualifiers("RangedInt", format)
    val formatsThatNeedQualifierChecks:Array[String] = splitLists._1
    //val formatsNotNeedingQualifiers = splitLists._2
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "length" => length = qualifiers(i).toInt
        case "min" => min = qualifiers(i).toInt
        case "max" => max = qualifiers(i).toInt
      }
    }
    padNumberWithZeros(randomInteger(min,max).toString,length)
  }
}
