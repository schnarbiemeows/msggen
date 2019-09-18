/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import scala.collection.mutable.ArrayBuffer


/**
  * class for validating the formats specified for the
  * money data type
  */
object MoneyQualifiersValidator extends Validator {

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
  def validateEnumMoneyQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
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
  def validateRandomMoneyQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    for(i <- 0 until formatsThatNeedQualifierChecks.length) {
      // TODO - finish
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
  def validateExternalMoneyQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
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
  def validateRangeMoneyQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    for(i <- 0 until formatsThatNeedQualifierChecks.length) {
      // TODO - finish
    }
    (isValidated,message)
  }
}
