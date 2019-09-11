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
  * class for unit testing the IntLongFormatValidator class methods
  */
@Test
class IntLongQualifiersValidatorTest {

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
  def validateEnumIntQualifiersTest():Unit = {
    template.dataTypes = Array("EnumInt")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(Array("something"))
    val results:Tuple2[Boolean,String] = IntLongQualifiersValidator.validateEnumIntQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateRandomIntQualifiersTest():Unit = {
    template.dataTypes = Array("RandomInt")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = IntLongQualifiersValidator.validateRandomIntQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateExternalIntQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalInt")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(Array("C:\\home\\schnarbies\\output\\ssns.txt"))
    val results:Tuple2[Boolean,String] = IntLongQualifiersValidator.validateExternalIntQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateRangeIntQualifiersTest():Unit = {
    template.dataTypes = Array("RangedInt")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = IntLongQualifiersValidator.validateRangeIntQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateEnumLongQualifiersTest():Unit = {
    template.dataTypes = Array("EnumLong")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(Array("something"))
    val results:Tuple2[Boolean,String] = IntLongQualifiersValidator.validateEnumLongQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateRandomLongQualifiersTest():Unit = {
    template.dataTypes = Array("RandomLong")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = IntLongQualifiersValidator.validateRandomLongQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateExternalLongQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalLong")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(Array("C:\\home\\schnarbies\\output\\ssns.txt"))
    val results:Tuple2[Boolean,String] = IntLongQualifiersValidator.validateExternalLongQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateRangeLongQualifiersTest():Unit = {
    template.dataTypes = Array("RangedLong")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = IntLongQualifiersValidator.validateRangeLongQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }
}
