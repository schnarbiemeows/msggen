/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import com.scala.classes.exception._
import com.scala.classes.posos.{DataTypes, GenericRecordsTemplate, RecordsTemplate}
import com.scala.classes.utilities.{Configuration, DateUtils, LogUtil}

/**
  * this class is a validation class for validating that all of the data in an
  * input spreadsheet template is valid
  * @param template - Excel template that we are validating
  */
class ExcelDataSheetValidator(template: RecordsTemplate) extends Validator {

  /**
    * main method call for validating the spreadsheet template
    * @return - isValidated(Boolean)
    */
  override def validate(): Boolean = {
    val runStart = DateUtils.nowTime()
    var runStartLocal = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("entering ExcelDataSheetValidator.validate() method, validating the input template")
    var isValidated = true
    try {
      validateLengthsOfThreeHeaderRows()
      LogUtil.msggenMasterLoggerDEBUG("header rows validated")
      validateNoRowLengthsExceedsHeaderLength()
      LogUtil.msggenMasterLoggerDEBUG("no value rows exceed header lengths")
      validateTheDataTypes()
      LogUtil.msggenMasterLoggerDEBUG("data types validated")
      validateDataFormats()
      LogUtil.msggenMasterLoggerDEBUG("data formats validated")
      validateDataQualifiers()
      LogUtil.msggenMasterLoggerDEBUG("data values validated")

    } catch {
      case e:Exception => {LogUtil.msggenMasterLoggerERROR("Exception occurred : "+e+" , exiting program")}
        isValidated = false
    }

    val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
    LogUtil.logTime(s"ExcelDataSheetValidator validate() method time = ${runEnd._1} milliseconds")
    isValidated
  }

  /**
    * method that validates the length of all 3 header rows to make sure they
    * are the same length
    * @throws com.scala.classes.exception.MismatchedColumnLengthException
    * @return - true or false
    */
  @throws(classOf[MismatchedColumnLengthException])
  def validateLengthsOfThreeHeaderRows():Boolean = {
    var isValidated = true
    val fields = template.fields
    val dataTypes = template.dataTypes
    val formats = template.dataFormats
    if(fields.length!=dataTypes.length||dataTypes.length!=formats.length) {
      throw new MismatchedColumnLengthException(s"fields.length,dataTypes.length,and formats.length lengths do not match, [${fields.length},${dataTypes.length},${formats.length}]")
    }
    isValidated
  }

  /**
    * method that checks if any of the rows after the first 3 have columns past
    * the column length of the first 3(the meta-data header rows)
    * not currently being used
    * @throws com.scala.classes.exception.RowLengthsExceedsHeaderLengthException
    * @return - true or false
    */
  @throws(classOf[RowLengthsExceedsHeaderLengthException])
  def validateNoRowLengthsExceedsHeaderLength():Boolean = {
    var isValidated = true
    isValidated
  }

  /**
    * method that will validate that each field name is neither empty or null
    * also, if a config property mode4.fieldname.possiblecharacters is specified
    * it will make sure that each field name only contains those characters
    * @throws com.scala.classes.exception.InvalidFieldNameException
    * @return - true or false
    */
  @throws(classOf[InvalidFieldNameException])
  def validateFieldNames():Boolean = {
    var isValidated = true
    if(null!=template.asInstanceOf[GenericRecordsTemplate].properties.get(Configuration.MODE4_STRING_DATA_RULE)) {
      val accepatbleCharacters:String = template.asInstanceOf[GenericRecordsTemplate].properties.get(Configuration.MODE4_STRING_DATA_RULE).toString
      val fieldnames = template.fields
      for(item <- fieldnames) {
        val characters = item.toCharArray
        for(letter <- characters) {
          if(!accepatbleCharacters.contains(letter)) {
            throw new InvalidFieldNameException(s"field name ${item} has invalid charcters")
          }
        }
      }
    }
    isValidated
  }

  /**
    * method to check if each data type specified is valid
    * @throws com.scala.classes.exception.InvalidDataTypeException
    * @return - true or false
    */
  @throws(classOf[InvalidDataTypeException])
  def validateTheDataTypes():Boolean = {
    var isValidated = true
    val dataTypes = template.dataTypes
    for(item <- dataTypes) {
      if(!DataTypes.isValidDataType(item)) {
        throw new InvalidDataTypeException(s"data type ${item} is not a valid data type")
      }
    }
    isValidated
  }

  /**
    * method that the data formats specified are valid for each data type
    * @throws com.scala.classes.exception.InvalidDataFormatException
    * @return - true or false
    */
  @throws(classOf[InvalidDataFormatException])
  def validateDataFormats():Boolean = {
    var isValidated = true
    val dataTypes = template.dataTypes
    val formats = template.dataFormats
    var resultOfValidator:Tuple2[Boolean,String] = null
    for(i <- 0 until dataTypes.length) {
      val dataType = dataTypes(i)
      val format = formats(i)
      resultOfValidator = validateFormat(dataType,format)
      if(resultOfValidator._1==false) {
        throw new InvalidDataFormatException(s"index ${i} , ${dataType} format ${format} , is invalid : ${resultOfValidator._2}")
      } else {
        LogUtil.msggenMasterLoggerDEBUG(s"index ${i} , ${dataType} format ${format} , is valid")
      }
    }
    isValidated
  }

  /**
    * method that validates that the qualifiers for each data format are valid
    * @throws com.scala.classes.exception.InvalidDataFormatException
    * @return - true or false
    */
  @throws(classOf[InvalidDataQualifierException])
  def validateDataQualifiers():Boolean = {
    var isValidated = true
    val fields = template.fields
    val dataTypes = template.dataTypes
    val formats = template.dataFormats
    val allQualifiers = template.dataQualifiers
    var resultOfValidator:Tuple2[Boolean,String] = null
    for(i <- 0 until dataTypes.length){
      val field = fields(i)
      val dataType = dataTypes(i)
      val format = formats(i)
      val qualifiers = allQualifiers(i)
      dataType match {
        case "EnumString" => resultOfValidator = StringQualifiersValidator.validateEnumStringQualifiers(dataType,format,qualifiers)
        case "RandomString" => resultOfValidator = StringQualifiersValidator.validateRandomStringQualifiers(dataType,format,qualifiers)
        case "ExternalString" => resultOfValidator = StringQualifiersValidator.validateExternalStringQualifiers(dataType,format,qualifiers)
        case "RangedString" => resultOfValidator = StringQualifiersValidator.validateRangeStringQualifiers(dataType,format,qualifiers)
        case "EnumInt" => resultOfValidator = IntLongQualifiersValidator.validateEnumIntQualifiers(dataType,format,qualifiers)
        case "RandomInt" => resultOfValidator = IntLongQualifiersValidator.validateRandomIntQualifiers(dataType,format,qualifiers)
        case "ExternalInt" => resultOfValidator = IntLongQualifiersValidator.validateExternalIntQualifiers(dataType,format,qualifiers)
        case "RangedInt" => resultOfValidator = IntLongQualifiersValidator.validateRangeIntQualifiers(dataType,format,qualifiers)
        case "EnumLong" => resultOfValidator = IntLongQualifiersValidator.validateEnumLongQualifiers(dataType,format,qualifiers)
        case "RandomLong" => resultOfValidator = IntLongQualifiersValidator.validateRandomLongQualifiers(dataType,format,qualifiers)
        case "ExternalLong" => resultOfValidator = IntLongQualifiersValidator.validateExternalLongQualifiers(dataType,format,qualifiers)
        case "RangedLong" => resultOfValidator = IntLongQualifiersValidator.validateRangeLongQualifiers(dataType,format,qualifiers)
        case "EnumFloat" => resultOfValidator = FloatDoubleQualifiersValidator.validateEnumFloatQualifiers(dataType,format,qualifiers)
        case "RandomFloat" => resultOfValidator = FloatDoubleQualifiersValidator.validateRandomFloatQualifiers(dataType,format,qualifiers)
        case "ExternalFloat" => resultOfValidator = FloatDoubleQualifiersValidator.validateExternalFloatQualifiers(dataType,format,qualifiers)
        case "RangedFloat" => resultOfValidator = FloatDoubleQualifiersValidator.validateRangeFloatQualifiers(dataType,format,qualifiers)
        case "EnumDouble" => resultOfValidator = FloatDoubleQualifiersValidator.validateEnumDoubleQualifiers(dataType,format,qualifiers)
        case "RandomDouble" => resultOfValidator = FloatDoubleQualifiersValidator.validateRandomDoubleQualifiers(dataType,format,qualifiers)
        case "ExternalDouble" => resultOfValidator = FloatDoubleQualifiersValidator.validateExternalDoubleQualifiers(dataType,format,qualifiers)
        case "RangedDouble" => resultOfValidator = FloatDoubleQualifiersValidator.validateRangeDoubleQualifiers(dataType,format,qualifiers)
        case "EnumDate" => resultOfValidator = DateTimeQualifiersValidator.validateEnumDateQualifiers(dataType,format,qualifiers)
        case "RandomDate" => resultOfValidator = DateTimeQualifiersValidator.validateRandomDateQualifiers(dataType,format,qualifiers)
        case "ExternalDate" => resultOfValidator = DateTimeQualifiersValidator.validateExternalDateQualifiers(dataType,format,qualifiers)
        case "RangedDate" => resultOfValidator = DateTimeQualifiersValidator.validateRangeDateQualifiers(dataType,format,qualifiers)
        case "EnumDateTime" => resultOfValidator = DateTimeQualifiersValidator.validateEnumDateTimeQualifiers(dataType,format,qualifiers)
        case "RandomDateTime" => resultOfValidator = DateTimeQualifiersValidator.validateRandomDateTimeQualifiers(dataType,format,qualifiers)
        case "ExternalDateTime" => resultOfValidator = DateTimeQualifiersValidator.validateExternalDateTimeQualifiers(dataType,format,qualifiers)
        case "RangedDateTime" => resultOfValidator = DateTimeQualifiersValidator.validateRangeDateTimeQualifiers(dataType,format,qualifiers)
        case "EnumMoney" => resultOfValidator = MoneyQualifiersValidator.validateEnumMoneyQualifiers(dataType,format,qualifiers)
        case "RandomMoney" => resultOfValidator = MoneyQualifiersValidator.validateRandomMoneyQualifiers(dataType,format,qualifiers)
        case "ExternalMoney" => resultOfValidator = MoneyQualifiersValidator.validateExternalMoneyQualifiers(dataType,format,qualifiers)
        case "RangedMoney" => resultOfValidator = MoneyQualifiersValidator.validateRangeMoneyQualifiers(dataType,format,qualifiers)
      }
      if(resultOfValidator._1==false) {
        throw new InvalidDataQualifierException(s"field ${field} , has an invalid qualifier : ${resultOfValidator._2}")
      } else {
        LogUtil.msggenMasterLoggerDEBUG(s"all of the qualifiers for field : ${field} are valid")
      }
    }
    isValidated
  }

}
