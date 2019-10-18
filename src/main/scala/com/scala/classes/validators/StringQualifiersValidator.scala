/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import java.util.Properties

import com.scala.classes.utilities.{NumUtility, StringUtils}

import scala.collection.mutable.ArrayBuffer
/**
  * class for validating the formats specified for the
  * string data type
  */
object StringQualifiersValidator extends Validator {

  /**
    * main validation method
    * @param properties - singleton Properties object
    * @return - Boolean
    */
  override def validate(properties:Properties): Boolean = {true}

  /**
    * method to validate that the qualifiers for the EnumString data type are valid
    * @param dataType = data type name
    * @param format = data format(string of comma separated keywords)
    * @param qualifiers = array of qualifiers
    * @return (isValidated:Boolean,message:String)
    */
  def validateRandomStringQualifiers(dataType: String, format: String, qualifiers: ArrayBuffer[String]):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
    if(qualifiers.length!=formatsThatNeedQualifierChecks.length) {
      isValidated = false
      message = s"qualifiers.length : ${qualifiers.length} != : ${formatsThatNeedQualifierChecks.length} formatsThatNeedQualifierChecks.length for the ${dataType} data type"
    } else {
      for(i <- 0 until formatsThatNeedQualifierChecks.length) {
        formatsThatNeedQualifierChecks(i) match {
          case "length" => {
            if (!StringUtils.isInteger(qualifiers(i))) {
              isValidated = false
              message = s"qualifier specified for the length format is not an integer for the ${dataType} data type"
            }
          }
          case "chars" => {
            isValidated = true
            message = "not sure what we need to check for here"
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
    }
    (isValidated, message)
  }
}
