/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import java.util.Properties

import com.scala.classes.posos.GenericRecordsTemplate

import scala.collection.mutable.ArrayBuffer

/**
  * class used for createing mock objects to be used in tests
  * @param properties
  */
class ExcelSheetValidatorTestMocks(val properties: Properties) {

  /**
    *
    * @return
    */
  def getBlankTemplate(): GenericRecordsTemplate = {
    var template = new GenericRecordsTemplate(properties)
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
    var template = new GenericRecordsTemplate(properties)
    template.fields = Array("field1","field2","field3")
    template.dataTypes = Array("EnumString","EnumString","EnumString")
    template.dataFormats = Array("NONE","NONE","NONE")
    template.dataQualifiers = Array(ArrayBuffer())
    template
  }
}
