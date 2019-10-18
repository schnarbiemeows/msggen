/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import java.util.Properties

import com.scala.classes.exception._
import com.scala.classes.posos.{DataTypes, GenericRecordsTemplate, RecordsTemplate}
import com.scala.classes.utilities.{Configuration, DateUtils, LogUtil}

/**
  * this class is a validation class for validating that all of the data in an
  * input spreadsheet template is valid
  * @param template - Excel template that we are validating
  */
class ExcelDataSheetValidator(val mode: Int, template: RecordsTemplate) extends Validator {

  /**
    * main validation method for validating the spreadsheet object
    * @param properties - singleton Properties object
    * @return - Boolean
    */
  override def validate(properties: Properties): Boolean = {
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
          validatePrimaryKeyFile(properties)
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
  def validatePrimaryKeyFile(properties: Properties):Boolean = {
    var isValidated = valdateFileExists(properties.getProperty(Configuration.MODE1_OUTPUT_FILE))
    if(!isValidated) {
      throw new FileNotFoundException(s"primary Key File is not present")
    }
    val numberOfOutputFiles:Int = properties.getProperty(Configuration.MODE4_NUM_FILES).toString.toInt
    if(numberOfOutputFiles!=1) {
      throw new FileNotFoundException(s"you have to specify only 1 file for the mode4.numfiles configuration in order to use the primary key feature")
    }
    val numberOfOutputRecords:Int = properties.getProperty(Configuration.MODE4_NUM_RECORDS).toString.toInt
    val numberOfPrimaryValues:Int = properties.getProperty(Configuration.MODE1_NUM_PRIMARY_KEYS_TO_MAKE).toString.toInt
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
        case "EnumString" => resultOfValidator = StringQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomString" => resultOfValidator = StringQualifiersValidator.validateRandomStringQualifiers(dataType,format,qualifiers)
        case "ExternalString" => resultOfValidator = StringQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
        case "EnumInt" => resultOfValidator = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomInt" => resultOfValidator = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(dataType,format,qualifiers)
        case "ExternalInt" => resultOfValidator = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
        case "EnumLong" => resultOfValidator = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomLong" => resultOfValidator = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(dataType,format,qualifiers)
        case "ExternalLong" => resultOfValidator = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
        case "EnumFloat" => resultOfValidator = DecimalNumberQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomFloat" => resultOfValidator = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(dataType,format,qualifiers)
        case "ExternalFloat" => resultOfValidator = DecimalNumberQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
        case "EnumDouble" => resultOfValidator = DecimalNumberQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomDouble" => resultOfValidator = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(dataType,format,qualifiers)
        case "ExternalDouble" => resultOfValidator = DecimalNumberQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
        case "EnumDate" => resultOfValidator = DateTimeQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomDate" => resultOfValidator = DateTimeQualifiersValidator.validateRandomDateQualifiers(dataType,format,qualifiers)
        case "ExternalDate" => resultOfValidator = DateTimeQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
        case "EnumDateTime" => resultOfValidator = DateTimeQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomDateTime" => resultOfValidator = DateTimeQualifiersValidator.validateRandomDateTimeQualifiers(dataType,format,qualifiers)
        case "ExternalDateTime" => resultOfValidator = DateTimeQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
        case "EnumMoney" => resultOfValidator = MoneyQualifiersValidator.validateEnumTypeQualifiers(dataType,format,qualifiers)
        case "RandomMoney" => resultOfValidator = MoneyQualifiersValidator.validateRandomMoneyQualifiers(dataType,format,qualifiers)
        case "ExternalMoney" => resultOfValidator = MoneyQualifiersValidator.validateExternalTypeQualifiers(dataType,format,qualifiers)
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
