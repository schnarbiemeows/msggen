/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import java.util.Properties

import com.scala.classes.posos.GenericRecordsTemplate

class ExcelSheetValidatorTestMocks(val properties: Properties) {

  def getBasicTemplate(): GenericRecordsTemplate = {
    var template = new GenericRecordsTemplate(properties)
    template.fields = Array("field1","field2","field3")
    template.dataTypes = Array("EnumString","EnumString","EnumString")
    template.dataFormats = Array("NONE","NONE","NONE")
    template
  }
}
