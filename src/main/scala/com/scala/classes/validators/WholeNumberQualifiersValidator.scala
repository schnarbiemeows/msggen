/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import com.scala.classes.utilities.{NumUtility, StringUtils}

import scala.collection.mutable.ArrayBuffer


/**
  * class used for validating the formats specified for the
  * Int and Long data types
  */
object WholeNumberQualifiersValidator extends Validator {

  /**
    * main validation method
    * @return - Boolean
    */
  override def validate(): Boolean = {true}

  /**
    * method to validate that the qualifiers for the RandomInt and RandomLong data types are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateRandomWholeNumberQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    var hasmin:Boolean = false
    var hasmax:Boolean = false
    var minval:Long = 0
    var maxval:Long = 0
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    if(qualifiers.length!=formatsThatNeedQualifierChecks.length) {
      isValidated = false
      message = s"qualifiers.length : ${qualifiers.length} != : ${formatsThatNeedQualifierChecks.length} formatsThatNeedQualifierChecks.length for the ${dataType} data type"
    } else {
      for (i <- 0 until formatsThatNeedQualifierChecks.length) {
        formatsThatNeedQualifierChecks(i) match {
          case "length" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the length format is not a whole number for the ${dataType} data type"
            }
          }
          case "min" => {
            if (!StringUtils.isLong(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the min format is not a whole number for the ${dataType} data type"
            } else {
              hasmin = true
              minval = qualifiers(i).toLong
            }
          }
          case "max" => {
            if (!StringUtils.isLong(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the max format is not a whole number for the ${dataType} data type"
            } else {
              hasmax = true
              maxval = qualifiers(i).toLong
            }
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
    (isValidated,message)
  }

  def validateRangedWholeNumberQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    var haslinbase:Boolean = false
    var haslinadd:Boolean = false
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    if(qualifiers.length!=formatsThatNeedQualifierChecks.length) {
      isValidated = false
      message = s"qualifiers.length : ${qualifiers.length} != : ${formatsThatNeedQualifierChecks.length} formatsThatNeedQualifierChecks.length for the ${dataType} data type"
    } else {
      for (i <- 0 until formatsThatNeedQualifierChecks.length) {
        formatsThatNeedQualifierChecks(i) match {
          case "length" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the length format is not a whole number for the ${dataType} data type"
            }
          }
          case "linbase" => {
            if (!StringUtils.isLong(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the linbase format is not a whole number for the ${dataType} data type"
            } else {
              haslinbase = true
            }
          }
          case "linadd" => {
            if (!StringUtils.isLong(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the linadd format is not a whole number for the ${dataType} data type"
            } else {
              haslinadd = true
            }
          }
        }
      }
      if(isValidated) {
        if(haslinbase&&(!haslinadd)||(!haslinbase)&&haslinadd) {
          isValidated = false
          message = s"${dataType} data type must have values specified for both linbase and linadd, or neither specified"
        }
      }
    }
    (isValidated,message)
  }

}
