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
    *
    * @param qualifiers
    * @return
    */
  def makeEnumInt(qualifiers:ArrayBuffer[String]):String = {
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
  def makeRandomInt(format: String,qualifiers:ArrayBuffer[String]):String = {
    // TODO - add formatting code
    val min = -10
    val max = 10
    randomInteger(min,max).toString
  }

  /**
    *
    * @param qualifiers
    * @return
    */
  def makeExternalInt(qualifiers:ArrayBuffer[String]):String = {
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
  def makeRangedInt(format: String,qualifiers:ArrayBuffer[String]):String = {
    // TODO - add formatting code
    val min = -10
    val max = 10
    randomInteger(min,max).toString
  }
}
