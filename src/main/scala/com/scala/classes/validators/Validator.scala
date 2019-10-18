/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import java.io.File
import java.util.Properties

import com.scala.classes.posos.DataTypeFormats
import com.scala.classes.utilities.{Configuration, NumUtility, StringUtils}

import scala.collection.mutable.ArrayBuffer


/**
  * trait for the handling of various validations
  */
trait Validator {

  /**
    * main validation method
    * @return - Boolean
    */
  def validate(properties:Properties):Boolean

  /**
    * this method will search the DataTypeFormats.keywords Map according to the
    * dataTypeName key, and make sure that all of the keywords listed in the format
    * string are found in the Set for that data type
    * @param dataTypeName - data type
    * @param format - list of formats for this data type
    * @return - (isValidated,message)
    */
  def validateFormat(dataTypeName:String, format:String):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    val formatSet:Set[String] = DataTypeFormats.keywords(dataTypeName)
    if (format != "NONE") {
      val keywordsSpecified = format.split(Configuration.DELIMITTER1)
      for(key <- keywordsSpecified) {
        if (!formatSet.contains(key)) {
          isValidated = false
          message = s"key ${key} given is not an allowable key"
        }
      }
    }
    (isValidated,message)
  }

  /**
    * this method will filter out any keywords in the format string that do not need qualifiers
    * the return array should index the same as the values array, so returnArray(0)
    * will correspond to dataValues(x)(0),for example
    * @param dataTypeName - data type
    * @param format - list of formats for this data type
    * @return - (isValidated,message)
    */
  def filterQualifiers(dataTypeName:String, format:String):Array[String] = {
    val keywords:Array[String] = format.split(Configuration.DELIMITTER1)
    val qualifiers:Set[String] = DataTypeFormats.keysThatNeedQualifiers(dataTypeName)
    var returnArray:Array[String] = keywords.filter(rec => qualifiers.contains(rec))
    returnArray
  }

  /**
    * this method is used for all External data types
    * this method will test to see if the specified file actually exists
    * it makes no assumptions as to whether the path was correctly specified
    * for the particular operating system
    * @param filename = full path name to the file
    * @return - true or false
    */
  def valdateFileExists(filename:String):Boolean = {
    var exists = false
    val f = new File(filename)
    if(f.isFile) {
      exists = true
    }
    exists
  }

  /**
    * method to validate that the qualifiers for any Enum data type are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateEnumTypeQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    if(qualifiers.length<=formatsThatNeedQualifierChecks.length) {
      isValidated = false
      message = s"no values specified for the ${dataType} data type"
    } else {
      for(i <- 0 until formatsThatNeedQualifierChecks.length) {
        formatsThatNeedQualifierChecks(i) match {
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
    }
    (isValidated,message)
  }

  /**
    * method to validate that the qualifiers for any External data type are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateExternalTypeQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    if(qualifiers.length<=formatsThatNeedQualifierChecks.length) {
      isValidated = false
      message = s"no file specified for the ${dataType} data type"
    } else {
      for(i <- 0 until formatsThatNeedQualifierChecks.length) {
        formatsThatNeedQualifierChecks(i) match {
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
      val filepath = qualifiers(qualifiers.length-1)
      if(!valdateFileExists(filepath)) {
        isValidated = false
        message = "file path specified is incorrect, or the file does not exist"
      }
    }
    (isValidated,message)
  }
}
