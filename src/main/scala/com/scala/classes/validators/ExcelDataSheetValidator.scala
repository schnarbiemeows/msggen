/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import com.scala.classes.exception._
import com.scala.classes.posos.{DataTypeFormats, DataTypes, GenericRecordsTemplate, RecordsTemplate}
import com.scala.classes.utilities.{Configuration, DateUtils, LogUtil}

/**
  * this class is a validation class for validating that all of the data in an
  * input spreadsheet template is valid
  * @param template
  */
class ExcelDataSheetValidator(template: RecordsTemplate) extends Validator {

  /**
    * main method call for validating the spreadsheet template
    * @return
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

    } catch {
      case e:Exception => {LogUtil.msggenMasterLoggerERROR("Exception occurred : "+e+" , exiting program")}
        isValidated = false
    }

    val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
    LogUtil.logTime(s"ExcelDataSheetValidator validate() method time = ${runEnd._1} milliseconds")
    isValidated
  }

  /**
    *
    * @throws com.scala.classes.exception.MismatchedColumnLengthException
    * @return
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
    * @return
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
    * @return
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
    * @return
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
    *
    * @throws com.scala.classes.exception.InvalidDataFormatException
    * @return
    */
  @throws(classOf[InvalidDataFormatException])
  def validateDataFormats():Boolean = {
    var isValidated = true
    val dataTypes = template.dataTypes
    val formats = template.dataFormats
    var resultOfValidator:Tuple2[Boolean,String] = null
    for(i <- 0 until dataTypes.length){
      val dataType = dataTypes(i)
      val format = formats(i)
      dataType match {
        case "EnumString" => resultOfValidator = StringFormatValidator.validateEnumStringFormat(format)
        case "RandomString" => resultOfValidator = StringFormatValidator.validateRandomStringFormat(format)
        case "ExternalString" => resultOfValidator = StringFormatValidator.validateExternalStringFormat(format)
        case "RangedString" => resultOfValidator = StringFormatValidator.validateRangeStringFormat(format)
        case "EnumInt" => resultOfValidator = IntLongFormatValidator.validateEnumIntFormat(format)
        case "RandomInt" => resultOfValidator = IntLongFormatValidator.validateRandomIntFormat(format)
        case "ExternalInt" => resultOfValidator = IntLongFormatValidator.validateExternalIntFormat(format)
        case "RangedInt" => resultOfValidator = IntLongFormatValidator.validateRangeIntFormat(format)
        case "EnumLong" => resultOfValidator = IntLongFormatValidator.validateEnumLongFormat(format)
        case "RandomLong" => resultOfValidator = IntLongFormatValidator.validateRandomLongFormat(format)
        case "ExternalLong" => resultOfValidator = IntLongFormatValidator.validateExternalLongFormat(format)
        case "RangedLong" => resultOfValidator = IntLongFormatValidator.validateRangeLongFormat(format)
        case "EnumFloat" => resultOfValidator = FloatDoubleFormatValidator.validateEnumFloatFormat(format)
        case "RandomFloat" => resultOfValidator = FloatDoubleFormatValidator.validateRandomFloatFormat(format)
        case "ExternalFloat" => resultOfValidator = FloatDoubleFormatValidator.validateExternalFloatFormat(format)
        case "RangedFloat" => resultOfValidator = FloatDoubleFormatValidator.validateRangeFloatFormat(format)
        case "EnumDouble" => resultOfValidator = FloatDoubleFormatValidator.validateEnumDoubleFormat(format)
        case "RandomDouble" => resultOfValidator = FloatDoubleFormatValidator.validateRandomDoubleFormat(format)
        case "ExternalDouble" => resultOfValidator = FloatDoubleFormatValidator.validateExternalDoubleFormat(format)
        case "RangedDouble" => resultOfValidator = FloatDoubleFormatValidator.validateRangeDoubleFormat(format)
        case "EnumDate" => resultOfValidator = DateTimeFormatValidator.validateEnumDateFormat(format)
        case "RandomDate" => resultOfValidator = DateTimeFormatValidator.validateRandomDateFormat(format)
        case "ExternalDate" => resultOfValidator = DateTimeFormatValidator.validateExternalDateFormat(format)
        case "RangedDate" => resultOfValidator = DateTimeFormatValidator.validateRangeDateFormat(format)
        case "EnumTime" => resultOfValidator = DateTimeFormatValidator.validateEnumTimeFormat(format)
        case "RandomTime" => resultOfValidator = DateTimeFormatValidator.validateRandomTimeFormat(format)
        case "ExternalTime" => resultOfValidator = DateTimeFormatValidator.validateExternalTimeFormat(format)
        case "RangedTime" => resultOfValidator = DateTimeFormatValidator.validateRangeTimeFormat(format)
        case "EnumMoney" => resultOfValidator = MoneyFormatValidator.validateEnumMoneyFormat(format)
        case "RandomMoney" => resultOfValidator = MoneyFormatValidator.validateRandomMoneyFormat(format)
        case "ExternalMoney" => resultOfValidator = MoneyFormatValidator.validateExternalMoneyFormat(format)
        case "RangedMoney" => resultOfValidator = MoneyFormatValidator.validateRangeMoneyFormat(format)
      }
      if(resultOfValidator._1==false) {
        throw new InvalidDataFormatException(s"index ${i} , ${dataType} format ${format} , is invalid : ${resultOfValidator._2}")
      } else {
        LogUtil.msggenMasterLoggerDEBUG(s"index ${i} , ${dataType} format ${format} , is valid")
      }
    }

    isValidated
  }

  def validateValuesData():Boolean = {
    var isValidated = true
    // TODO - finish
    isValidated
  }

  def validatePresenceOfExternalFiles():Boolean = {
    var isValidated = true
    // TODO - finish
    isValidated
  }


































}
