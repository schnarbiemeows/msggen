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
    *
    * @param qualifiers
    * @return
    */
  def makeEnumString(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    *
    * @param format
    * @param qualifiers
    * @return
    */
  def makeRandomString(format: String,qualifiers:ArrayBuffer[String]):String = {
    // TODO - add formatting code
    val length = 10
    randomAlphaNumeric(length)
  }

  /**
    *
    * @param qualifiers
    * @return
    */
  def makeExternalString(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    *
    * @param format
    * @param qualifiers
    * @return
    */
  def makeRangedString(format: String,qualifiers:ArrayBuffer[String]):String = {
    // TODO - add formatting code
    val length = 10
    randomAlphaNumeric(length)
  }
}
