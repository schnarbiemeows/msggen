/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different String types
  */
object StringTypeMaker{

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
    // TODO - add formatting code
    val length = 10
    randomAlphaNumeric(length)
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
    * this method generates a random RangedString
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRangedString(format: String,qualifiers:ArrayBuffer[String]):String = {
    // TODO - add formatting code
    val length = 10
    randomAlphaNumeric(length)
  }
}
