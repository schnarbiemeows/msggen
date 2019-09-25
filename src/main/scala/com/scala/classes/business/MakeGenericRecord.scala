/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import com.scala.classes.generators.{IntTypeGenerator, LongTypeGenerator, StringTypeGenerator}
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
        case "RangedString" => fieldValues(i) = StringTypeGenerator.makeRangedString(format, qualifiers)
        case "EnumInt" => fieldValues(i) = IntTypeGenerator.makeEnumInt(qualifiers)
        case "RandomInt" => fieldValues(i) = IntTypeGenerator.makeRandomInt(format,qualifiers)
        case "ExternalInt" => fieldValues(i) = IntTypeGenerator.makeExternalInt(qualifiers)
        case "RangedInt" => fieldValues(i) = IntTypeGenerator.makeRangedInt(format,qualifiers)
        case "EnumLong" => fieldValues(i) = LongTypeGenerator.makeEnumLong(qualifiers)
        case "RandomLong" => fieldValues(i) = LongTypeGenerator.makeRandomLong(format,qualifiers)
        case "ExternalLong" => fieldValues(i) = LongTypeGenerator.makeExternalLong(qualifiers)
        case "RangedLong" => fieldValues(i) = LongTypeGenerator.makeRangedLong(format,qualifiers)
        case _ => LogUtil.msggenMasterLoggerDEBUG("Data Type not found")
      }
    }
    new GenericRecord(fields,fieldValues)
  }
}
