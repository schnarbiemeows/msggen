/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

/**
  *
  */
object StringTypeMaker{

  /**
    *
    * @param qualifiers
    * @return
    */
  def makeEnumString(qualifiers:Array[String]):String = {
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
  def makeRandomString(format: String,qualifiers:Array[String]):String = {
    // TODO - add formatting code
    val length = 10
    randomAlphaNumeric(length)
  }

  /**
    *
    * @param qualifiers
    * @return
    */
  def makeExternalString(qualifiers:Array[String]):String = {
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
  def makeRangedString(format: String,qualifiers:Array[String]):String = {
    // TODO - add formatting code
    val length = 10
    randomAlphaNumeric(length)
  }
}
