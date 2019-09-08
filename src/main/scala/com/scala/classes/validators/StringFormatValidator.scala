/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import com.scala.classes.posos.DataTypeFormats
import com.scala.classes.utilities.{Configuration, StringUtils}

/**
  *
  */
object StringFormatValidator {

  /**
    * method to validate that the specified format rule(s) is(are) correct
    * right now,
    * @param format = format specified
    * @return (isValidated:Boolean,message:String)
    */
  def validateEnumStringFormat(format:String):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    // TODO - finish
    (isValidated,message)
  }

  /**
    * method that validates the rules for a RandomString data type
    * @param format = format specified  
    * @return (isValidated:Boolean,message:String)
    */
  def validateRandomStringFormat(format:String):Tuple2[Boolean,String] = {
    var isValidated = true
    var message: String = "NONE"
    if (format != "NONE") {
      val items = format.split(Configuration.DELIMITTER1)
      for (item <- items) {
        val oneOrTwoItems: Array[String] = item.split(Configuration.EQUALS)
        // if this item is a key/value pair, then the array will have size 2
        if (oneOrTwoItems.length == 2) {
          /**
            * key/value pair rules
            */
          val key = oneOrTwoItems(0)
          val value = oneOrTwoItems(1)
          if (!DataTypeFormats.stringRuleKeys.contains(key)) {
            isValidated = false
            message = s"key ${key} given is not an allowable key"
          } else if (key == "length") {
            /**
              * keyword "length"
              */
            // check to see if anything is specified for value
            if (value == null || value.isEmpty) {
              isValidated = false
              message = s"nothing specified for the value for given key : ${key}}"
            }
            // check if the length value is a number
            else if (!StringUtils.isInteger(value)) {
              isValidated = false
              message = s"value ${value} given for length is not a number"
              // make sure the number is positive
            } else if (Integer.parseInt(value) <= 0) {
              isValidated = false
              message = s"value ${value} must be a positive number"
            }
          } else if(key == "chars") {
            /**
              * keyword "chars"
              */
            // check to see if anything is specified for value
            if (value == null || value.isEmpty) {
              isValidated = false
              message = s"nothing specified for the value for given key : ${key}}"
            }
          }
          // otherwise, if the size is 1, then it should be a keyword
        } else if (oneOrTwoItems.length == 1) {
          /**
            * keyword rules
            */
          val keyword = oneOrTwoItems(0)
          if (!DataTypeFormats.stringRuleKeywords.contains(keyword)) {
            isValidated = false
            message = s"keyword ${keyword} given is not an allowable keyword"
          }
        } else {
          /**
            * multiple "=" were specified
            */
          isValidated = false
          message = "multiple equals sign in the key/value pair"
        }
      }
    }
    (isValidated, message)
  }

  /**
    *
    * @param format = format specified
    * @return (isValidated:Boolean,message:String)
    */
  def validateExternalStringFormat(format:String):Tuple2[Boolean,String] = {
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
  def validateRangeStringFormat(format:String):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    // TODO - finish
    (isValidated,message)
  }

}
