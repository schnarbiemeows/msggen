/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

/**
  * class that represent a record(or Row) in a data file(or table)
  * @param flds - the fields of the record or row
  * @param fldValues - the values of the record, or row
  */
class GenericRecord(val flds: Array[String],val fldValues: Array[String]) extends Record {
  override val fields: Array[String] = flds
  override val fieldValues: Array[String] = fldValues

}
