/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

/**
  * class for validating the formats specified for the
  * money data type
  */
object MoneyFormatValidator {

  /**
    *
    * @param format = format specified
    * @return (isValidated:Boolean,message:String)
    */
  def validateEnumMoneyFormat(format:String):Tuple2[Boolean,String] = {
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
  def validateRandomMoneyFormat(format:String):Tuple2[Boolean,String] = {
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
  def validateExternalMoneyFormat(format:String):Tuple2[Boolean,String] = {
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
  def validateRangeMoneyFormat(format:String):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    // TODO - finish
    (isValidated,message)
  }
}
