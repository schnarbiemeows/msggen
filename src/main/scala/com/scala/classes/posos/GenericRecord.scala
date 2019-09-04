/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

class GenericRecord(val flds: Array[String],val fldValues: Array[String]) extends Record {
  override val fields: Array[String] = flds
  override val fieldValues: Array[String] = fldValues
}
