/*
 * Created 2019 by Dylan Kessler 
 */

package com.scala.classes.validators

import java.util.Properties

import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.utilities.PropertyLoader
import org.junit.Assert._
import org.junit.{Before, Test}

import scala.collection.mutable.ArrayBuffer

/**
  * class for unit testing the DateTimeFormatValidator class methods
  */
@Test
class DateTimeQualifiersValidatorTest {

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
  def validateEnumDateQualifiersTest():Unit = {
    template.dataTypes = Array("EnumDate")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("something"))
    val results:Tuple2[Boolean,String] = DateTimeQualifiersValidator.validateEnumDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateRandomDateQualifiersTest():Unit = {
    template.dataTypes = Array("RandomDate")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = DateTimeQualifiersValidator.validateRandomDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateExternalDateQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalDate")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("C:\\home\\schnarbies\\output\\ssns.txt"))
    val results:Tuple2[Boolean,String] = DateTimeQualifiersValidator.validateExternalDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateRangeDateQualifiersTest():Unit = {
    template.dataTypes = Array("RangedDate")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = DateTimeQualifiersValidator.validateRangeDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateEnumTimeQualifiersTest():Unit = {
    template.dataTypes = Array("EnumTime")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("something"))
    val results:Tuple2[Boolean,String] = DateTimeQualifiersValidator.validateEnumTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateRandomTimeQualifiersTest():Unit = {
    template.dataTypes = Array("RandomTime")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = DateTimeQualifiersValidator.validateRandomTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateExternalTimeQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalTime")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("C:\\home\\schnarbies\\output\\ssns.txt"))
    val results:Tuple2[Boolean,String] = DateTimeQualifiersValidator.validateExternalTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateRangeTimeQualifiersTest():Unit = {
    template.dataTypes = Array("RangedTime")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = DateTimeQualifiersValidator.validateRangeTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }
}
