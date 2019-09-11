/*
 * Created 2019 by Dylan Kessler 
 */

package com.scala.classes.validators

import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.utilities.PropertyLoader
import org.junit.{Before, Test}
import org.junit.Assert._
import java.util.Properties

/**
  * class for unit testing the FloatDoubleFormatValidator class methods
  */
@Test
class FloatDoubleQualifiersValidatorTest {

  var properties:Properties = _
  var template:GenericRecordsTemplate = _

  /**
    *
    */
  @Before
  def initialize():Unit = {
    properties = PropertyLoader.getProperties("config.properties")
    var mocks:ExcelSheetValidatorTestMocks = new ExcelSheetValidatorTestMocks(properties)
    template = mocks.getBlankTemplate()

  }

  /**
    *
    */
  @Test
  def validateEnumFloatQualifiersTest():Unit = {
    template.dataTypes = Array("EnumFloat")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = FloatDoubleQualifiersValidator.validateEnumFloatQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateRandomFloatQualifiersTest():Unit = {
    template.dataTypes = Array("RandomFloat")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = FloatDoubleQualifiersValidator.validateRandomFloatQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateExternalFloatQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalFloat")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(Array("C:\\home\\schnarbies\\output\\ssns.txt"))
    val results:Tuple2[Boolean,String] = FloatDoubleQualifiersValidator.validateExternalFloatQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateRangeFloatQualifiersTest():Unit = {
    template.dataTypes = Array("RangedFloat")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = FloatDoubleQualifiersValidator.validateRangeFloatQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateEnumDoubleQualifiersTest():Unit = {
    template.dataTypes = Array("EnumDouble")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = FloatDoubleQualifiersValidator.validateEnumDoubleQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateRandomDoubleQualifiersTest():Unit = {
    template.dataTypes = Array("RandomDouble")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = FloatDoubleQualifiersValidator.validateRandomDoubleQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateExternalDoubleQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalDouble")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(Array("C:\\home\\schnarbies\\output\\ssns.txt"))
    val results:Tuple2[Boolean,String] = FloatDoubleQualifiersValidator.validateExternalDoubleQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateRangeDoubleQualifiersTest():Unit = {
    template.dataTypes = Array("RangedDouble")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = FloatDoubleQualifiersValidator.validateRangeDoubleQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }
}
