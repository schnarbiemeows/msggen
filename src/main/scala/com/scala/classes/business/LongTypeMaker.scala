/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different Long types
  */
object LongTypeMaker {

  /**
    * this method generates a random EnumLong
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeEnumLong(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * this method generates a random RandomLong
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRandomLong(format: String,qualifiers:ArrayBuffer[String]):String = {
    // TODO - add formatting code
    val min = -10
    val max = 10
    randomLong(min,max).toString
  }

  /**
    * this method generates a random ExternalLong
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeExternalLong(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * this method generates a random RangedLong
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRangedLong(format: String,qualifiers:ArrayBuffer[String]):String = {
    // TODO - add formatting code
    val min = -10
    val max = 10
    randomLong(min,max).toString
  }
}
