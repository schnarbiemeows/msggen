/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import java.time.{LocalDate, LocalDateTime}
import java.util.Properties

import com.scala.classes.utilities.DateUtils

import scala.collection.mutable.ArrayBuffer

/**
  * class used to validate the formats specified for the Date and time
  * data types
  */
object DateTimeQualifiersValidator extends Validator {

  /**
    * main validation method
    * @param properties - singleton Properties object
    * @return - Boolean
    */
  override def validate(properties: Properties): Boolean = {true}

  /**
    * this method checks to make sure that there is at least 1 value
    * for the particular Enum data type
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateEnumDateQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    if(qualifiers.length==0) {
      isValidated = false
      message = "no values specified"
    }
    (isValidated,message)
  }

  /**
    * this method checks to make sure that there is at least 1 value
    * for the particular Enum data type
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateEnumDateTimeQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
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
  def validateRandomDateQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    var formatSpecifiedValid:Boolean = false
    var formatSpecified:String = ""
    var endWasSpecified:Boolean = false
    var endSpecifiedValid:Boolean = false
    var endSpecified:LocalDate = null
    var startWasSpecified:Boolean = false
    var startSpecifiedValid:Boolean = false
    var startSpecified:LocalDate = null
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    if(qualifiers.length!=formatsThatNeedQualifierChecks.length) {
      isValidated = false
      message = s"qualifiers.length : ${qualifiers.length} != : ${formatsThatNeedQualifierChecks.length} formatsThatNeedQualifierChecks.length for the ${dataType} data type"
    } else {
      for (i <- 0 until formatsThatNeedQualifierChecks.length) {
        formatsThatNeedQualifierChecks(i) match {
          case "format" => {
            if (!DateUtils.isDateFormatValid(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the Date Time format is not valid for the ${dataType} data type"
            } else {
              formatSpecifiedValid = true
              formatSpecified = qualifiers(i)
            }
          }
          case "end" => {
            endWasSpecified = true
          }
          case "start" => {
            startWasSpecified = true
          }
          case default => {
            isValidated = false
            message = s"${default} is not a valid qualifier for the ${dataType} data type"
          }
        }
      }
      if (formatSpecifiedValid && (startWasSpecified || endWasSpecified)) {
        for (i <- 0 until formatsThatNeedQualifierChecks.length) {
          formatsThatNeedQualifierChecks(i) match {
            case "start" => {
              if (!DateUtils.willStringParseToLocalDate(qualifiers(i), formatSpecified)) {
                isValidated = false
                message = s"start DateTime specified is not valid for the ${dataType} data type"
              } else {
                startSpecifiedValid = true
                startSpecified = DateUtils.getDateFromString(qualifiers(i), formatSpecified)
              }
            }
            case "end" => {
              if (!DateUtils.willStringParseToLocalDate(qualifiers(i), formatSpecified)) {
                isValidated = false
                message = s"end DateTime specified is not valid for the ${dataType} data type"
              } else {
                endSpecifiedValid = true
                endSpecified = DateUtils.getDateFromString(qualifiers(i), formatSpecified)
              }
            }
            case default => {}
          }
        }
        if (startWasSpecified && endWasSpecified) {
          if (!(startSpecifiedValid && endSpecifiedValid)) {
            isValidated = false
            message = s"start Date and/or end Date specified is not valid for the format specified for the ${dataType} data type"
          } else {
            if (startSpecified.compareTo(endSpecified) > 0) {
              isValidated = false
              message = s"the specified start Date comes after the specified end Date for the ${dataType} data type"
            }
          }
        }
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
  def validateRandomDateTimeQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    var formatSpecifiedValid:Boolean = false
    var formatSpecified:String = ""
    var endWasSpecified:Boolean = false
    var endSpecifiedValid:Boolean = false
    var endSpecified:LocalDateTime = null
    var startWasSpecified:Boolean = false
    var startSpecifiedValid:Boolean = false
    var startSpecified:LocalDateTime = null
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    if(qualifiers.length!=formatsThatNeedQualifierChecks.length) {
      isValidated = false
      message = s"qualifiers.length : ${qualifiers.length} != : ${formatsThatNeedQualifierChecks.length} formatsThatNeedQualifierChecks.length for the ${dataType} data type"
    } else {
      for (i <- 0 until formatsThatNeedQualifierChecks.length) {
        formatsThatNeedQualifierChecks(i) match {
          case "format" => {
            if (!DateUtils.isDateTimeFormatValid(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the Date Time format is not valid for the ${dataType} data type"
            } else {
              formatSpecifiedValid = true
              formatSpecified = qualifiers(i)
            }
          }
          case "end" => {
            endWasSpecified = true
          }
          case "start" => {
            startWasSpecified = true
          }
          case default => {
            isValidated = false
            message = s"${default} is not a valid qualifier for the ${dataType} data type"
          }
        }
      }
      if (formatSpecifiedValid && (startWasSpecified || endWasSpecified)) {
        for (i <- 0 until formatsThatNeedQualifierChecks.length) {
          formatsThatNeedQualifierChecks(i) match {
            case "start" => {
              if (!DateUtils.willStringParseToLocalDateTime(qualifiers(i), formatSpecified)) {
                isValidated = false
                message = s"start DateTime specified is not valid for the ${dataType} data type"
              } else {
                startSpecifiedValid = true
                startSpecified = DateUtils.getDateTimeFromString(qualifiers(i), formatSpecified)
              }
            }
            case "end" => {
              if (!DateUtils.willStringParseToLocalDateTime(qualifiers(i), formatSpecified)) {
                isValidated = false
                message = s"end DateTime specified is not valid for the ${dataType} data type"
              } else {
                endSpecifiedValid = true
                endSpecified = DateUtils.getDateTimeFromString(qualifiers(i), formatSpecified)
              }
            }
            case default => {}
          }
        }
        if (startWasSpecified && endWasSpecified) {
          if (!(startSpecifiedValid && endSpecifiedValid)) {
            isValidated = false
            message = s"start DateTime and/or end DateTime specified is not valid for the format specified for the ${dataType} data type"
          } else {
            if (startSpecified.compareTo(endSpecified) > 0) {
              isValidated = false
              message = s"the specified start DateTime comes after the specified end DateTime for the ${dataType} data type"
            }
          }
        }
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
  def validateExternalDateQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
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
  def validateExternalDateTimeQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
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
}
