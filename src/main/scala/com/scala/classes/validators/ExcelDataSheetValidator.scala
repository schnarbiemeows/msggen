/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import com.scala.classes.exception._
import com.scala.classes.posos.{DataTypes, RecordsTemplate}
import com.scala.classes.utilities.{Configuration, DateUtils, LogUtil, PropertyLoader}

/**
  * this class is a validation class for validating that all of the data in an
  * input spreadsheet template is valid
  * @param template - Excel template that we are validating
  */
class ExcelDataSheetValidator(val mode: Int, template: RecordsTemplate) extends Validator {

  /**
    * main validation method for validating the spreadsheet object

    * @return - Boolean
    */
  override def validate(): Boolean = {
    val runStart = DateUtils.nowTime()
    var runStartLocal = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("entering ExcelDataSheetValidator.validate() method, validating the input template")
    var isValidated = true
    try {
      validateLengthsOfThreeHeaderRows()
      LogUtil.msggenMasterLoggerDEBUG("header rows validated")
      validateTheDataTypes()
      LogUtil.msggenMasterLoggerDEBUG("data types validated")
      if(mode==4 || mode==5 || mode == 7 || mode==8) {
        validateDataFormats()
        LogUtil.msggenMasterLoggerDEBUG("data formats validated")
        validateDataQualifiers()
        LogUtil.msggenMasterLoggerDEBUG("data values validated")
        if(mode == 7) {
          validatePrimaryKeyFile()
          LogUtil.msggenMasterLoggerDEBUG("primary key file validated")
        }
      }
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
    * method that checks if the primary key file(made using mode 1)
    * is present in the file system
    * @throws com.scala.classes.exception.FileNotFoundException
    * @return - true or false
    */
  @throws(classOf[FileNotFoundException])
  def validatePrimaryKeyFile():Boolean = {
    var isValidated = valdateFileExists(PropertyLoader.getProperty(Configuration.MODE1_OUTPUT_FILE))
    if(!isValidated) {
      throw new FileNotFoundException(s"primary Key File is not present")
    }
    val numberOfOutputFiles:Int = PropertyLoader.getProperty(Configuration.MODE4_NUM_FILES).toString.toInt
    if(numberOfOutputFiles!=1) {
      throw new FileNotFoundException(s"you have to specify only 1 file for the mode4.numfiles configuration in order to use the primary key feature")
    }
    val numberOfOutputRecords:Int = PropertyLoader.getProperty(Configuration.MODE4_NUM_RECORDS).toString.toInt
    val numberOfPrimaryValues:Int = PropertyLoader.getProperty(Configuration.MODE1_NUM_PRIMARY_KEYS_TO_MAKE).toString.toInt
    if(numberOfOutputRecords!=numberOfPrimaryValues) {
      throw new FileNotFoundException(s"you have to specify the same value for both the mode1.numberofprimariestomake and mode4.numberofrecordsperfile configurations in order to use the primary key feature")
    }
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
    // TODO - either implement this method or remove it;; it is not currently being used
    var isValidated = true
    if(PropertyLoader.getProperty(Configuration.MODE4_STRING_DATA_RULE).toBoolean) {
      val accepatbleCharacters:String = PropertyLoader.getProperty(Configuration.MODE4_STRING_DATA_RULE).toString
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
    var resultOfValidator:Option[Tuple2[Boolean,String]] = None
    for(i <- 0 until dataTypes.length) {
      val dataType = dataTypes(i)
      val format = formats(i)
      resultOfValidator = Some(validateFormat(dataType,format))
      if(resultOfValidator.get._1==false) {
        throw new InvalidDataFormatException(s"index ${i} , ${dataType} format ${format} , is invalid : ${resultOfValidator.get._2}")
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
    for(i <- 0 until dataTypes.length){
      val field = fields(i)
      val dataType = dataTypes(i)
      val format = formats(i)
      val qualifiers = allQualifiers(i)
      val resultOfValidator:Tuple2[Boolean,String] = dataType match {
        case "EnumString" => StringQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomString" => StringQualifiersValidator.validateRandomStringQualifiers(dataType,format,qualifiers)
        case "ExternalString" => StringQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
        case "EnumInt" => WholeNumberQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomInt" => WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(dataType,format,qualifiers)
        case "RangedInt" => WholeNumberQualifiersValidator.validateRangedWholeNumberQualifiers(dataType,format,qualifiers)
        case "ExternalInt" => WholeNumberQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
        case "EnumLong" => WholeNumberQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomLong" => WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(dataType,format,qualifiers)
        case "RangedLong" => WholeNumberQualifiersValidator.validateRangedWholeNumberQualifiers(dataType,format,qualifiers)
        case "ExternalLong" => WholeNumberQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
        case "EnumFloat" => DecimalNumberQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomFloat" => DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(dataType,format,qualifiers)
        case "ExternalFloat" => DecimalNumberQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
        case "EnumDouble" => DecimalNumberQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomDouble" => DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(dataType,format,qualifiers)
        case "ExternalDouble" => DecimalNumberQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
        case "EnumDate" => DateTimeQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomDate" => DateTimeQualifiersValidator.validateRandomDateQualifiers(dataType,format,qualifiers)
        case "ExternalDate" => DateTimeQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
        case "EnumDateTime" => DateTimeQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomDateTime" => DateTimeQualifiersValidator.validateRandomDateTimeQualifiers(dataType,format,qualifiers)
        case "ExternalDateTime" => DateTimeQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
        case "EnumMoney" => MoneyQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomMoney" => MoneyQualifiersValidator.validateRandomMoneyQualifiers(dataType,format,qualifiers)
        case "ExternalMoney" => MoneyQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
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
