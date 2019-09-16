/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

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
        case "EnumString" => fieldValues(i) = StringTypeMaker.makeEnumString(qualifiers)
        case "RandomString" => fieldValues(i) = StringTypeMaker.makeRandomString(format, qualifiers)
        case "ExternalString" => fieldValues(i) = StringTypeMaker.makeExternalString(qualifiers)
        case "RangedString" => fieldValues(i) = StringTypeMaker.makeRangedString(format, qualifiers)
        case _ => LogUtil.msggenMasterLoggerDEBUG("Data Type not found")
      }
    }
    new GenericRecord(fields,fieldValues)
  }
}
