/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

/**
  * utility class for manipulating strings
  */
object StringUtils {

  /**
    * method to check if a String is a number
    * @param s = string to check
    * @return true or false
    */
  def isInteger(s: String):Boolean = {
    var isInteger:Boolean = true
    try {
      Integer.parseInt(s)
    } catch {
      case e:NumberFormatException => isInteger = false
    }
    isInteger
  }

  def removeColons(input: String):String = {
    input.replaceAll(":","")
  }
}
