/*
 * Created 2019 by Dylan Kessler
 */

/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different String types
  */
object StringTypeGenerator{

  /**
    * this method generates a random EnumString
    * @param qualifiers
    * @return - string
    */
  def makeEnumString(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * this method generates a random RandomString
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRandomString(format: String,qualifiers:ArrayBuffer[String]):String = {
    var length:Int = 0
    var chars:String = null
    var toUpper:Boolean = false
    var toLower:Boolean = false
    val splitLists:Tuple2[Array[String],Array[String]] = filterQualifiers("RandomString", format)
    val formatsThatNeedQualifierChecks:Array[String] = splitLists._1
    val formatsNotNeedingQualifiers = splitLists._2
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "length" => length = qualifiers(i).toInt
        case "chars" => chars = qualifiers(i).toString
      }
    }
    for (i <- 0 until formatsNotNeedingQualifiers.length) {
      formatsNotNeedingQualifiers(i) match {
        case "upper" => toUpper = true
        case "lower" => toLower = true
      }
    }
    var result = if(chars==null) randomAlphaNumeric(length) else randomAlphaNumeric(length,chars)
    result = if(toUpper) result.toUpperCase else if(toLower) result.toLowerCase else result
    result
  }

  /**
    * this method generates a random ExternalString
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeExternalString(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * Set("length","chars","upper","lower")
    * this method generates a random RangedString
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRangedString(format: String,qualifiers:ArrayBuffer[String]):String = {
    var length:Int = 0
    var chars:String = null
    var toUpper:Boolean = false
    var toLower:Boolean = false
    val splitLists:Tuple2[Array[String],Array[String]] = filterQualifiers("RangedString", format)
    val formatsThatNeedQualifierChecks:Array[String] = splitLists._1
    val formatsNotNeedingQualifiers = splitLists._2
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "length" => length = qualifiers(i).toInt
        case "chars" => chars = qualifiers(i).toString
      }
    }
    for (i <- 0 until formatsNotNeedingQualifiers.length) {
      formatsNotNeedingQualifiers(i) match {
        case "upper" => toUpper = true
        case "lower" => toLower = true
      }
    }
    var result = if(chars==null) randomAlphaNumeric(length) else randomAlphaNumeric(length,chars)
    result = if(toUpper) result.toUpperCase else if(toLower) result.toLowerCase else result
    result
  }
}
