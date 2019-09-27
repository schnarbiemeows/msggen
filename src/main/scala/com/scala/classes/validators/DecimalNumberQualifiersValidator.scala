/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import com.scala.classes.utilities.StringUtils

import scala.collection.mutable.ArrayBuffer

/**
  * class used for validating the formats sepcified for the Float
  * and Double data types
  */
object DecimalNumberQualifiersValidator extends Validator {

  /**
    * main validation method - currently not used
    * TODO - maybe refactor to remove this reference
    * @return - Boolean
    */
  override def validate(): Boolean = {true}

  /**
    * method to validate that the qualifiers for the EnumDouble data type are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateEnumDecimalNumberQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    if(qualifiers.length==0) {
      isValidated = false
      message = "no values specified"
    }
    (isValidated,message)
  }

  /**
    * method to validate that the qualifiers for the RandomDouble data type are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateRandomDecimalNumberQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    var hasmin:Boolean = false
    var hasmax:Boolean = false
    var minval:Double = 0
    var maxval:Double = 0
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    if(qualifiers.length!=formatsThatNeedQualifierChecks.length) {
      isValidated = false
      message = s"qualifiers.length : ${qualifiers.length} != : ${formatsThatNeedQualifierChecks.length} formatsThatNeedQualifierChecks.length for the RandomDouble data type"
    } else {
      for(i <- 0 until formatsThatNeedQualifierChecks.length) {
        formatsThatNeedQualifierChecks(i) match {
          case "length" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the length format is not an integer for the ${dataType} data type"
            }
          }
          case "signDigits" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"the significant digit qualifier specified is not an integer for the ${dataType} data type"
            }
          }
          case "min" => {
            if (!StringUtils.isDouble(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the min format is not an integer for the ${dataType} data type"
            } else {
              hasmin = true
              minval = qualifiers(i).toDouble
            }
          }
          case "max" => {
            if (!StringUtils.isDouble(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the max format is not an integer for the ${dataType} data type"
            } else {
              hasmax = true
              maxval = qualifiers(i).toDouble
            }
          }
          case default => {
            isValidated = false
            message = s"${default} is not a valid qualifier for the ${dataType} data type"
          }
        }
      }
      val hasRoundUp = format.contains("roundup")
      val hasRoundDown = format.contains("rounddown")
      val hasRound = format.contains("roundhalf")
      if(hasRoundUp&&hasRoundDown||hasRoundDown&&hasRound||hasRound&&hasRoundUp) {
        isValidated = false
        message = s"${dataType} data type can only specify one of the following: roundup, rounddown, or roundhalf"
      } else {
        if(!(hasmin&&hasmax)) {
          isValidated = false
          message = s"${dataType} data type must have both a valid(numeric) minimum and a valid(numeric) maximum value specified"
        } else {
          if(minval>maxval) {
            isValidated = false
            message = s"min value = ${minval} is greater than max value = ${maxval} for the ${dataType} data type"
          }
        }
      }
    }
    (isValidated,message)
  }

  /**
    * method to validate that the qualifiers for the ExternalDouble data type are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateExternalDecimalNumberQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
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
