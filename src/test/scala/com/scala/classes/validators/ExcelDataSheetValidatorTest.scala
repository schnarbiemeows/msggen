/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import java.util.Properties

import com.scala.classes.exception.{InvalidDataTypeException, InvalidFieldNameException, MismatchedColumnLengthException}
import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.utilities.PropertyLoader
import org.junit.Assert._
import org.junit.{Before, Test}

/**
  * class for unit testing the ExcelDataSheetValidator class methods
  */
@Test
class ExcelDataSheetValidatorTest {

  var properties:Properties = _
  var template:GenericRecordsTemplate = _
  var validator:ExcelDataSheetValidator = _
  var mocks:ExcelSheetValidatorTestMocks = _

  /**
    *
    */
  @Before
  def initialize():Unit = {
    properties = PropertyLoader.getProperties("config.properties")
    mocks = new ExcelSheetValidatorTestMocks(properties)
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

  @Test
  def testDataFormats():Unit = {
    template = mocks.getBlankTemplate()
    template.dataTypes = Array("EnumString")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    var isValid:Boolean = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RandomString")
    template.dataFormats = Array("length","chars","upper","lower")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("ExternalString")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RangedString")
    template.dataFormats = Array("length","chars","upper","lower")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("EnumInt")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RandomInt")
    template.dataFormats = Array("length","min","max")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("ExternalInt")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RangedInt")
    template.dataFormats = Array("length","min","max")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("EnumLong")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RandomLong")
    template.dataFormats = Array("length","min","max")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("ExternalLong")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RangedLong")
    template.dataFormats = Array("length","min","max")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("EnumFloat")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RandomFloat")
    template.dataFormats = Array("length","signDigits","min","max","roundup","rounddown","round")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("ExternalFloat")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RangedFloat")
    template.dataFormats = Array("length","signDigits","min","max","roundup","rounddown","round")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("EnumDouble")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RandomDouble")
    template.dataFormats = Array("length","signDigits","min","max","roundup","rounddown","round")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("ExternalDouble")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RangedDouble")
    template.dataFormats = Array("length","signDigits","min","max","roundup","rounddown","round")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("EnumDate")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RandomDate")
    template.dataFormats = Array("startdate","enddate","format")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("ExternalDate")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RangedDate")
    template.dataFormats = Array("startdate","enddate","format")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("EnumDateTime")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RandomDateTime")
    template.dataFormats = Array("starttime","endtime","format")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("ExternalDateTime")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RangedDateTime")
    template.dataFormats = Array("starttime","endtime","format")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("EnumMoney")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RandomMoney")
    template.dataFormats = Array("length","min","max","roundup","rounddown","round")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("ExternalMoney")
    template.dataFormats = Array("NONE")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

    template = mocks.getBlankTemplate()
    template.dataTypes = Array("RangedMoney")
    template.dataFormats = Array("length","min","max","roundup","rounddown","round")
    validator = new ExcelDataSheetValidator(template)
    isValid = validator.validateDataFormats()
    assertTrue(isValid)

  }
}
