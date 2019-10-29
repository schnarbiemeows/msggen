/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import com.scala.classes.posos.GenericRecordsTemplate

import scala.collection.mutable.ArrayBuffer

/**
  * class used for creating mock objects to be used in tests

  */
class ExcelSheetValidatorTestMocks() {

  /**
    *
    * @return
    */
  def getBlankTemplate(): GenericRecordsTemplate = {
    val template = new GenericRecordsTemplate()
    template.fields = Array()
    template.dataTypes = Array()
    template.dataFormats = Array()
    template.dataQualifiers = Array(ArrayBuffer())
    template
  }
  /**
    *
    * @return
    */
  def getBasicTemplate(): GenericRecordsTemplate = {
    val template = new GenericRecordsTemplate()
    template.fields = Array("field1","field2","field3")
    template.dataTypes = Array("EnumString","EnumString","EnumString")
    template.dataFormats = Array("NONE","NONE","NONE")
    template.dataQualifiers = Array(ArrayBuffer())
    template
  }
}
