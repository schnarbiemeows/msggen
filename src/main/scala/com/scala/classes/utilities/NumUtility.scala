/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

/**
  * Utility class for Numeric utilities
  */
object NumUtility extends Randomizer {

  val ONE:Double = 1.0
  val ZERO:Double = 0.0
  /**
    * method to initialize nothing atm
    */
  override def initialize(): Unit = {}

  /**
    * method to convert a Long or Int to a String and pad it with leading zeroes, if needed
    * this is used to pad subscriber and account IDs
    * if the length specified is negative, it won't fail because subId will never be null
    * so it will always be a length > 0
    * if the length is smaller than the actual length of the number to convert, just
    * return the number
    * @param subId = subscriber ID to convert
    * @param length = length to pad to
    * @return subId.toString
    */
  def padIntToString(subId: Long, length: Int): String = {
    var intLength = subId.toString.length
    if(intLength>=length) {
      subId.toString
    }
    var subIdStr:String = subId.toString
    for(count <- 0 until length-intLength) {
      subIdStr = "0".concat(subIdStr)
    }
    subIdStr
  }

  /**
    * method for truncating a Double to an Int
    * if the result is greater than Int.MaxValue, it Int.MaxValue is returned
    * @param multiplicationFactor = multiplication factor
    * @param percentage = percentage to convert
    * @return number
    */
  def convertPercentageToInt(multiplicationFactor: Int, percentage: Double):Int = {
    val number:Int = (multiplicationFactor * percentage).toInt
    number
  }
}
