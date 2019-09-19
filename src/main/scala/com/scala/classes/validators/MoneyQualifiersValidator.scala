/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import com.scala.classes.utilities.StringUtils

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
    * Set("length","min","max","roundup","rounddown","roundhalf")
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateRandomMoneyQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    var hasmin:Boolean = false
    var hasmax:Boolean = false
    var minval:Double = 0
    var maxval:Double = 0
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    if(qualifiers.length!=formatsThatNeedQualifierChecks.length) {
      isValidated = false
      message = s"qualifiers.length : ${qualifiers.length} != : ${formatsThatNeedQualifierChecks.length} formatsThatNeedQualifierChecks.length for the RandomMoney data type"
    } else {
      for(i <- 0 until formatsThatNeedQualifierChecks.length) {
        formatsThatNeedQualifierChecks(i) match {
          case "length" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the length format is not an integer for the RandomMoney data type"
            }
          }
          case "min" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the min format is not an integer for the RandomMoney data type"
            } else {
              hasmin = true
              minval = qualifiers(i).toDouble
            }
          }
          case "max" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the max format is not an integer for the RandomMoney data type"
            } else {
              hasmax = true
              maxval = qualifiers(i).toDouble
            }
          }
          case default => {
            isValidated = false
            message = s"${default} is not a valid qualifier for the RandomMoney data type"
          }
        }
      }
      val hasRoundUp = format.contains("roundup")
      val hasRoundDown = format.contains("rounddown")
      val hasRound = format.contains("roundhalf")
      if(hasRoundUp&&hasRoundDown||hasRoundDown&&hasRound||hasRound&&hasRoundUp) {
        isValidated = false
        message = s"RandomMoney data type can only specify one of the following: roundup, rounddown, or roundhalf"
      } else {
        if(!(hasmin&&hasmax)) {
          isValidated = false
          message = s"RandomMoney data type must have both a minimum and a maximum value specified"
        } else {
          if(minval>maxval) {
            isValidated = false
            message = s"min value = ${minval} is greater than max value = ${maxval} for the RandomMoney data type"
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
    * Set("length","min","max","roundup","rounddown","roundhalf")
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateRangeMoneyQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    var hasmin:Boolean = false
    var hasmax:Boolean = false
    var minval:Double = 0
    var maxval:Double = 0
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    if(qualifiers.length!=formatsThatNeedQualifierChecks.length) {
      isValidated = false
      message = s"qualifiers.length : ${qualifiers.length} != : ${formatsThatNeedQualifierChecks.length} formatsThatNeedQualifierChecks.length for the RangedMoney data type"
    } else {
      for(i <- 0 until formatsThatNeedQualifierChecks.length) {
        formatsThatNeedQualifierChecks(i) match {
          case "length" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the length format is not an integer for the RangedMoney data type"
            }
          }
          case "min" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the min format is not an integer for the RangedMoney data type"
            } else {
              hasmin = true
              minval = qualifiers(i).toDouble
            }
          }
          case "max" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the max format is not an integer for the RangedMoney data type"
            } else {
              hasmax = true
              maxval = qualifiers(i).toDouble
            }
          }
          case default => {
            isValidated = false
            message = s"${default} is not a valid qualifier for the RangedMoney data type"
          }
        }
      }
      val hasRoundUp = format.contains("roundup")
      val hasRoundDown = format.contains("rounddown")
      val hasRound = format.contains("roundhalf")
      if(hasRoundUp&&hasRoundDown||hasRoundDown&&hasRound||hasRound&&hasRoundUp) {
        isValidated = false
        message = s"RangedMoney data type can only specify one of the following: roundup, rounddown, or roundhalf"
      } else {
        if(!(hasmin&&hasmax)) {
          isValidated = false
          message = s"RangedMoney data type must have both a minimum and a maximum value specified"
        } else {
          if(minval>maxval) {
            isValidated = false
            message = s"min value = ${minval} is greater than max value = ${maxval} for the RangedMoney data type"
          }
        }
      }
    }
    (isValidated,message)
  }
}
