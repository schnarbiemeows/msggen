/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import com.scala.classes.utilities.StringUtils

import scala.collection.mutable.ArrayBuffer


/**
  * class used for validating the formats specified for the
  * Int and Long data types
  */
object IntLongQualifiersValidator extends Validator {

  /**
    * main validation method - currently not used
    * TODO - maybe refactor to remove this reference
    * @return
    */
  override def validate(): Boolean = {true}

  /**
    * method to validate that the qualifiers for the EnumInt data type are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateEnumIntQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    if(qualifiers.length==0) {
      isValidated = false
      message = "no values specified"
    }
    (isValidated,message)
  }

  /**
    * method to validate that the qualifiers for the EnumLong data type are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateEnumLongQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    if(qualifiers.length==0) {
      isValidated = false
      message = "no values specified"
    }
    (isValidated,message)
  }


  /**
    * method to validate that the qualifiers for the RandomInt data type are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateRandomIntQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    var hasmin:Boolean = false
    var hasmax:Boolean = false
    var minval:Int = 0
    var maxval:Int = 0
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    if(qualifiers.length!=formatsThatNeedQualifierChecks.length) {
      isValidated = false
      message = s"qualifiers.length : ${qualifiers.length} != : ${formatsThatNeedQualifierChecks.length} formatsThatNeedQualifierChecks.length for the RandomInt data type"
    } else {
      for (i <- 0 until formatsThatNeedQualifierChecks.length) {
        formatsThatNeedQualifierChecks(i) match {
          case "length" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the length format is not an integer for the RandomInt data type"
            }
          }
          case "min" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the min format is not an integer for the RandomInt data type"
            } else {
              hasmin = true
              minval = qualifiers(i).toInt
            }
          }
          case "max" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the max format is not an integer for the RandomInt data type"
            } else {
              hasmax = true
              maxval = qualifiers(i).toInt
            }
          }
          case default => {
            isValidated = false
            message = s"${default} is not a valid qualifier for the RandomInt data type"
          }
        }
      }
      if(!(hasmin&&hasmax)) {
        isValidated = false
        message = s"RandomInt data type must have both a valid(numeric) minimum and a valid(numeric) maximum value specified"
      } else {
        if(minval>maxval) {
          isValidated = false
          message = s"min value = ${minval} is greater than max value = ${maxval} for the RandomInt data type"
        }
      }
    }
    (isValidated,message)
  }

  /**
    * method to validate that the qualifiers for the RandomLong data type are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateRandomLongQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    var hasmin:Boolean = false
    var hasmax:Boolean = false
    var minval:Long = 0
    var maxval:Long = 0
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    if(qualifiers.length!=formatsThatNeedQualifierChecks.length) {
      isValidated = false
      message = s"qualifiers.length : ${qualifiers.length} != : ${formatsThatNeedQualifierChecks.length} formatsThatNeedQualifierChecks.length for the RandomLong data type"
    } else {
      for (i <- 0 until formatsThatNeedQualifierChecks.length) {
        formatsThatNeedQualifierChecks(i) match {
          case "length" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the length format is not an integer for the RandomLong data type"
            }
          }
          case "min" => {
            if (!StringUtils.isLong(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the min format is not an integer for the RandomLong data type"
            } else {
              hasmin = true
              minval = qualifiers(i).toLong
            }
          }
          case "max" => {
            if (!StringUtils.isLong(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the max format is not an integer for the RandomLong data type"
            } else {
              hasmax = true
              maxval = qualifiers(i).toLong
            }
          }
          case default => {
            isValidated = false
            message = s"${default} is not a valid qualifier for the RandomLong data type"
          }
        }
      }
      if(!(hasmin&&hasmax)) {
        isValidated = false
        message = s"RandomLong data type must have both a valid(numeric) minimum and a valid(numeric) maximum value specified"
      } else {
        if(minval>maxval) {
          isValidated = false
          message = s"min value = ${minval} is greater than max value = ${maxval} for the RandomLong data type"
        }
      }
    }
    (isValidated,message)
  }

  /**
    * method to validate that the qualifiers for the ExternalInt data type are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateExternalIntQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
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
    * method to validate that the qualifiers for the ExternalLong data type are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateExternalLongQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
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
    * method to validate that the qualifiers for the RangedInt data type are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateRangeIntQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    var hasmin:Boolean = false
    var hasmax:Boolean = false
    var minval:Int = 0
    var maxval:Int = 0
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    if(qualifiers.length!=formatsThatNeedQualifierChecks.length) {
      isValidated = false
      message = s"qualifiers.length : ${qualifiers.length} != : ${formatsThatNeedQualifierChecks.length} formatsThatNeedQualifierChecks.length for the RangedInt data type"
    } else {
      for (i <- 0 until formatsThatNeedQualifierChecks.length) {
        formatsThatNeedQualifierChecks(i) match {
          case "length" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the length format is not an integer for the RangedInt data type"
            }
          }
          case "min" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the min format is not an integer for the RangedInt data type"
            } else {
              hasmin = true
              minval = qualifiers(i).toInt
            }
          }
          case "max" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the max format is not an integer for the RangedInt data type"
            } else {
              hasmax = true
              maxval = qualifiers(i).toInt
            }
          }
          case default => {
            isValidated = false
            message = s"${default} is not a valid qualifier for the RangedInt data type"
          }
        }
      }
      if(!(hasmin&&hasmax)) {
        isValidated = false
        message = s"RangedInt data type must have both a valid(numeric) minimum and a valid(numeric) maximum value specified"
      } else {
        if(minval>maxval) {
          isValidated = false
          message = s"min value = ${minval} is greater than max value = ${maxval} for the RangedInt data type"
        }
      }
    }
    (isValidated,message)
  }

  /**
    * method to validate that the qualifiers for the RangedLong data type are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateRangeLongQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    var hasmin:Boolean = false
    var hasmax:Boolean = false
    var minval:Long = 0
    var maxval:Long = 0
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    if(qualifiers.length!=formatsThatNeedQualifierChecks.length) {
      isValidated = false
      message = s"qualifiers.length : ${qualifiers.length} != : ${formatsThatNeedQualifierChecks.length} formatsThatNeedQualifierChecks.length for the RangedLong data type"
    } else {
      for (i <- 0 until formatsThatNeedQualifierChecks.length) {
        formatsThatNeedQualifierChecks(i) match {
          case "length" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the length format is not an integer for the RangedLong data type"
            }
          }
          case "min" => {
            if (!StringUtils.isLong(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the min format is not an integer for the RangedLong data type"
            } else {
              hasmin = true
              minval = qualifiers(i).toLong
            }
          }
          case "max" => {
            if (!StringUtils.isLong(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the max format is not an integer for the RangedLong data type"
            } else {
              hasmax = true
              maxval = qualifiers(i).toLong
            }
          }
          case default => {
            isValidated = false
            message = s"${default} is not a valid qualifier for the RangedLong data type"
          }
        }
      }
      if(!(hasmin&&hasmax)) {
        isValidated = false
        message = "RangedLong data type must have both a valid(numeric) minimum and a valid(numeric) maximum value specified"
      } else {
        if(minval>maxval) {
          isValidated = false
          message = s"min value = ${minval} is greater than max value = ${maxval} for the RangedLong data type"
        }
      }
    }
    (isValidated,message)
  }

}
