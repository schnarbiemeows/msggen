/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import java.time.{LocalDate, LocalDateTime}

import com.scala.classes.utilities.{DateUtils, NumUtility, StringUtils}

import scala.collection.mutable.ArrayBuffer

/**
  * class used to validate the formats specified for the Date and time
  * data types
  */
object DateTimeQualifiersValidator extends Validator {

  /**
    * main validation method
    * @return - Boolean
    */
  override def validate(): Boolean = {true}

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
    var endSpecified:Option[LocalDate] = None
    var startWasSpecified:Boolean = false
    var startSpecifiedValid:Boolean = false
    var startSpecified:Option[LocalDate] = None
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
          case "nullable" => {
            if (!StringUtils.isFloat(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the nullable format is not a percentage for the ${dataType} data type"
            } else if(qualifiers(i).toFloat>NumUtility.ONE) {
              isValidated = false
              message = s"nullable qualifier specified must for the ${dataType} data type must be less than 1.0"
            } else if(qualifiers(i).toFloat<NumUtility.ZERO) {
              isValidated = false
              message = s"nullable qualifier specified must for the ${dataType} data type must be greater than 0.0"
            }
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
                startSpecified = Some(DateUtils.getDateFromString(qualifiers(i), formatSpecified))
              }
            }
            case "end" => {
              if (!DateUtils.willStringParseToLocalDate(qualifiers(i), formatSpecified)) {
                isValidated = false
                message = s"end DateTime specified is not valid for the ${dataType} data type"
              } else {
                endSpecifiedValid = true
                endSpecified = Some(DateUtils.getDateFromString(qualifiers(i), formatSpecified))
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
            if (startSpecified.get.compareTo(endSpecified.get) > 0) {
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
    var endSpecified:Option[LocalDateTime] = None
    var startWasSpecified:Boolean = false
    var startSpecifiedValid:Boolean = false
    var startSpecified:Option[LocalDateTime] = None
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
          case "nullable" => {
            if (!StringUtils.isFloat(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the nullable format is not a percentage for the ${dataType} data type"
            } else if(qualifiers(i).toFloat>NumUtility.ONE) {
              isValidated = false
              message = s"nullable qualifier specified must for the ${dataType} data type must be less than 1.0"
            } else if(qualifiers(i).toFloat<NumUtility.ZERO) {
              isValidated = false
              message = s"nullable qualifier specified must for the ${dataType} data type must be greater than 0.0"
            }
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
                startSpecified = Some(DateUtils.getDateTimeFromString(qualifiers(i), formatSpecified))
              }
            }
            case "end" => {
              if (!DateUtils.willStringParseToLocalDateTime(qualifiers(i), formatSpecified)) {
                isValidated = false
                message = s"end DateTime specified is not valid for the ${dataType} data type"
              } else {
                endSpecifiedValid = true
                endSpecified = Some(DateUtils.getDateTimeFromString(qualifiers(i), formatSpecified))
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
            if (startSpecified.get.compareTo(endSpecified.get) > 0) {
              isValidated = false
              message = s"the specified start DateTime comes after the specified end DateTime for the ${dataType} data type"
            }
          }
        }
      }
    }
    (isValidated,message)
  }
}
