/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import com.scala.classes.exception.{InvalidDataTypeException, InvalidFieldNameException, MismatchedColumnLengthException}
import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.utilities.PropertyLoader
import org.junit.{Before, Test}
import org.junit.Assert._
import java.util.Properties

/**
  * class for unit testing the ExcelDataSheetValidator class methods
  */
@Test
class ExcelDataSheetValidatorTest {

  var properties:Properties = _
  var template:GenericRecordsTemplate = _
  var validator:ExcelDataSheetValidator = _

  @Before
  def initialize():Unit = {
    properties = PropertyLoader.getProperties("config.properties")
    var mocks:ExcelSheetValidatorTestMocks = new ExcelSheetValidatorTestMocks(properties)
    template = mocks.getBasicTemplate()

  }

  import org.junit.Test

  @Test(expected = classOf[MismatchedColumnLengthException])
  def testHeaderLengths():Unit = {
    validator = new ExcelDataSheetValidator(template)
    var isValid:Boolean = validator.validateLengthsOfThreeHeaderRows()
    assertTrue(isValid)
    template.fields = Array("field1","field2")
    validator = new ExcelDataSheetValidator(template)
    validator.validateLengthsOfThreeHeaderRows()
  }

  @Test(expected = classOf[InvalidFieldNameException])
  def testFieldNames():Unit = {
    template.fields = Array("field1","field2","field3")
    validator = new ExcelDataSheetValidator(template)
    var isValid:Boolean = validator.validateFieldNames()
    assertTrue(isValid)
    template.fields = Array("field1","field2","#$%^#")
    validator = new ExcelDataSheetValidator(template)
    validator.validateFieldNames()
  }

  @Test(expected = classOf[InvalidDataTypeException])
  def testDataTypes():Unit = {
    template.dataTypes = Array("EnumString","EnumString","EnumString")
    validator = new ExcelDataSheetValidator(template)
    var isValid:Boolean = validator.validateTheDataTypes()
    assertTrue(isValid)
    template.dataTypes = Array("String","String","String")
    validator = new ExcelDataSheetValidator(template)
    validator.validateTheDataTypes()
  }
}
