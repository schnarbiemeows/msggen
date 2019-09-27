/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import com.scala.classes.generators._
import com.scala.classes.posos.{GenericRecord, Record, RecordsTemplate}
import com.scala.classes.utilities.LogUtil

/**
  * this class is for making Record objects
  */
object MakeGenericRecord {

  /**
    * this method makes a Generic record from the meta-data contained in a
    * RecordsTemplate object
    * @param records - template that contains the meta-data for making our record
    * @return - Record = our new record
    */
  def makeRecord(records: RecordsTemplate):Record = {
    val fields = records.fields
    var fieldValues = new Array[String](fields.length)
    val dataTypes = records.dataTypes
    val formats = records.dataFormats
    val allQualifiers = records.dataQualifiers
    for(i <- 0 until fields.length) {
      val field = fields(i)
      val dataType = dataTypes(i)
      val format = formats(i)
      val qualifiers = allQualifiers(i)
      dataType match {
        case "EnumString" => fieldValues(i) = StringTypeGenerator.makeEnumString(qualifiers)
        case "RandomString" => fieldValues(i) = StringTypeGenerator.makeRandomString(format, qualifiers)
        case "ExternalString" => fieldValues(i) = StringTypeGenerator.makeExternalString(qualifiers)
        case "EnumInt" => fieldValues(i) = WholeNumberTypeGenerator.makeEnumWholeNumber(qualifiers)
        case "RandomInt" => fieldValues(i) = WholeNumberTypeGenerator.makeRandomWholeNumber(format,qualifiers)
        case "ExternalInt" => fieldValues(i) = WholeNumberTypeGenerator.makeExternalWholeNumber(qualifiers)
        case "EnumLong" => fieldValues(i) = WholeNumberTypeGenerator.makeEnumWholeNumber(qualifiers)
        case "RandomLong" => fieldValues(i) = WholeNumberTypeGenerator.makeRandomWholeNumber(format,qualifiers)
        case "ExternalLong" => fieldValues(i) = WholeNumberTypeGenerator.makeExternalWholeNumber(qualifiers)
        case "EnumFloat" => fieldValues(i) = DecimalNumberTypeGenerator.makeEnumDecimalNumber(qualifiers)
        case "RandomFloat" => fieldValues(i) = DecimalNumberTypeGenerator.makeRandomDecimalNumber(format,qualifiers)
        case "ExternalFloat" => fieldValues(i) = DecimalNumberTypeGenerator.makeExternalDecimalNumber(qualifiers)
        case "EnumDouble" => fieldValues(i) = DecimalNumberTypeGenerator.makeEnumDecimalNumber(qualifiers)
        case "RandomDouble" => fieldValues(i) = DecimalNumberTypeGenerator.makeRandomDecimalNumber(format,qualifiers)
        case "ExternalDouble" => fieldValues(i) = DecimalNumberTypeGenerator.makeExternalDecimalNumber(qualifiers)
        case "EnumDate" => fieldValues(i) = DateTypeGenerator.makeEnumDate(qualifiers)
        case "RandomDate" => fieldValues(i) = DateTypeGenerator.makeRandomDate(format,qualifiers)
        case "ExternalDate" => fieldValues(i) = DateTypeGenerator.makeExternalDate(qualifiers)
        case "EnumDateTime" => fieldValues(i) = DateTimeTypeGenerator.makeEnumDateTime(qualifiers)
        case "RandomDateTime" => fieldValues(i) = DateTimeTypeGenerator.makeRandomDateTime(format,qualifiers)
        case "ExternalDateTime" => fieldValues(i) = DateTimeTypeGenerator.makeExternalDateTime(qualifiers)
        case "EnumMoney" => fieldValues(i) = MoneyTypeGenerator.makeEnumMoney(qualifiers)
        case "RandomMoney" => fieldValues(i) = MoneyTypeGenerator.makeRandomMoney(format,qualifiers)
        case "ExternalMoney" => fieldValues(i) = MoneyTypeGenerator.makeExternalMoney(qualifiers)
        case _ => LogUtil.msggenMasterLoggerDEBUG("Data Type not found")
      }
    }
    new GenericRecord(fields,fieldValues)
  }
}
