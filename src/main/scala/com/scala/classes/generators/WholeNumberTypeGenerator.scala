/*
 * Created 2019 by Dylan Kessler
 */

/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different Long types
  */
object WholeNumberTypeGenerator {

  /**
    * this method generates a random EnumLong
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeEnumWholeNumber(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * this method generates a random Long number for both RandomLong and RandomInt
    * data types
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRandomWholeNumber(format: String, qualifiers:ArrayBuffer[String]):String = {
    var length:Int = 10
    var min:Long = 0
    var max:Long = 0
    val splitLists:Tuple2[Array[String],Array[String]] = filterQualifiers("RandomLong", format)
    val formatsThatNeedQualifierChecks:Array[String] = splitLists._1
    //val formatsNotNeedingQualifiers = splitLists._2
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "length" => length = qualifiers(i).toInt
        case "min" => min = qualifiers(i).toLong
        case "max" => max = qualifiers(i).toLong
      }
    }
    padNumberWithZeros(randomLong(min,max).toString,length)
  }

  /**
    * this method generates a random ExternalLong
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeExternalWholeNumber(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }
}
