/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different Int types
  */
object IntTypeMaker {

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
    // TODO - add formatting code
    val min = -10
    val max = 10
    randomInteger(min,max).toString
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
    // TODO - add formatting code
    val min = -10
    val max = 10
    randomInteger(min,max).toString
  }
}
