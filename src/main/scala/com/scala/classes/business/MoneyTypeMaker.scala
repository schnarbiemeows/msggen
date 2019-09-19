/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different Money types
  */
object MoneyTypeMaker {

  /**
    * this method generates a random EnumMoney
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeEnumMoney(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * this method generates a random RandomMoney
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRandomMoney(format: String,qualifiers:ArrayBuffer[String]):String = {
    // TODO - add formatting code
    val min:Double = -10
    val max:Double = 10
    randomMoney(min,max).toString
  }

  /**
    * this method generates a random ExternalMoney
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeExternalMoney(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * this method generates a random RangedMoney
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRangedMoney(format: String,qualifiers:ArrayBuffer[String]):String = {
    // TODO - add formatting code
    val min = -10
    val max = 10
    randomMoney(min,max).toString
  }
}
