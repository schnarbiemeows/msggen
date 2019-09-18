/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import scala.collection.mutable.ArrayBuffer

/**
  *
  */
object StringQualifiersValidator extends Validator {

  /**
    * main validation method - currently not used
    * TODO - maybe refactor to remove this reference
    * @return
    */
  override def validate(): Boolean = {true}

  /**
    * this method checks to make sure that there is at least 1 value
    * for the particular Enum data type
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateEnumStringQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    if(qualifiers.length==0) {
      isValidated = false
      message = "no values specified"
    }
    (isValidated,message)
  }

  /**
    *
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateRandomStringQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message: String = "NONE"
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    for(i <- 0 until formatsThatNeedQualifierChecks.length) {

    }
    /*if (format != "NONE") {
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
    }*/
    (isValidated, message)
  }

  /**
    *
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateExternalStringQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    if(qualifiers.length==0) {
      isValidated = false
      message = "has no file path specified"
    } else {
      val filepath = qualifiers(0)
      if(!valdateFileExists(filepath)) {
        isValidated = false
        message = "file path specified is incorrect, or the file does not exist"
      }
    }
    (isValidated,message)
  }

  /**
    *
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateRangeStringQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    for(i <- 0 until formatsThatNeedQualifierChecks.length) {
      // TODO - finish
    }
    (isValidated,message)
  }

}
