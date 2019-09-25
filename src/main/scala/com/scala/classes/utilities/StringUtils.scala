/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

/**
  * utility class for manipulating strings
  */
object StringUtils {

  /**
    * method to check if a String is an Int
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

  /**
    * method to check if a String is a Long
    * @param s = string to check
    * @return true or false
    */
  def isLong(s: String):Boolean = {
    var isLong:Boolean = true
    try {
      s.toLong
    } catch {
      case e:NumberFormatException => isLong = false
    }
    isLong
  }

  /**
    * method to check if a String is a Float
    * @param s = string to check
    * @return true or false
    */
  def isFloat(s: String):Boolean = {
    var isFloat:Boolean = true
    try {
        s.toFloat
    } catch {
      case e:NumberFormatException => isFloat = false
    }
    isFloat
  }

  /**
    * method to check if a String is a Double
    * @param s = string to check
    * @return true or false
    */
  def isDouble(s: String):Boolean = {
    var isDouble:Boolean = true
    try {
      s.toDouble
    } catch {
      case e:NumberFormatException => isDouble = false
    }
    isDouble
  }

  /**
    * method to remove the colons from a string
    * @param input - string to process
    * @return - string
    */
  def removeColons(input: String):String = {
    input.replaceAll(":","")
  }

}
