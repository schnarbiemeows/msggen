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
    *
    * @param qualifiers
    * @return
    */
  def makeEnumLong(qualifiers:ArrayBuffer[String]):String = {
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
  def makeRandomLong(format: String,qualifiers:ArrayBuffer[String]):String = {
    // TODO - add formatting code
    val min = -10
    val max = 10
    randomLong(min,max).toString
  }

  /**
    *
    * @param qualifiers
    * @return
    */
  def makeExternalLong(qualifiers:ArrayBuffer[String]):String = {
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
  def makeRangedLong(format: String,qualifiers:ArrayBuffer[String]):String = {
    // TODO - add formatting code
    val min = -10
    val max = 10
    randomLong(min,max).toString
  }
}
