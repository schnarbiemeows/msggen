/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

/**
  * class used to validate the formats specified for the Date and time
  * data types
  */
object DateTimeFormatValidator {

  /**
    *
    * @param format = format specified
    * @return (isValidated:Boolean,message:String)
    */
  def validateEnumDateFormat(format:String):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    // TODO - finish
    (isValidated,message)
  }

  /**
    *
    * @param format = format specified
    * @return (isValidated:Boolean,message:String)
    */
  def validateEnumTimeFormat(format:String):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    // TODO - finish
    (isValidated,message)
  }

  /**
    *
    * @param format = format specified
    * @return (isValidated:Boolean,message:String)
    */
  def validateRandomDateFormat(format:String):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    // TODO - finish
    (isValidated,message)
  }

  /**
    *
    * @param format = format specified
    * @return (isValidated:Boolean,message:String)
    */
  def validateRandomTimeFormat(format:String):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    // TODO - finish
    (isValidated,message)
  }

  /**
    *
    * @param format = format specified
    * @return (isValidated:Boolean,message:String)
    */
  def validateExternalDateFormat(format:String):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    // TODO - finish
    (isValidated,message)
  }

  /**
    *
    * @param format = format specified
    * @return (isValidated:Boolean,message:String)
    */
  def validateExternalTimeFormat(format:String):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    // TODO - finish
    (isValidated,message)
  }

  /**
    *
    * @param format = format specified
    * @return (isValidated:Boolean,message:String)
    */
  def validateRangeDateFormat(format:String):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    // TODO - finish
    (isValidated,message)
  }

  /**
    *
    * @param format = format specified
    * @return (isValidated:Boolean,message:String)
    */
  def validateRangeTimeFormat(format:String):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    // TODO - finish
    (isValidated,message)
  }
}
