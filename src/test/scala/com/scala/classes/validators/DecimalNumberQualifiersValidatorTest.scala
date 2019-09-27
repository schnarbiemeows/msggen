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
  * class for unit testing the DecimalNumberQualifiersValidator class methods
  */
@Test
class DecimalNumberQualifiersValidatorTest {

  var properties:Properties = _
  var template:GenericRecordsTemplate = _

  /**
    * initialization
    */
  @Before
  def initialize():Unit = {
    properties = PropertyLoader.getProperties("C:\\home\\schnarbies\\config\\config.properties")
    var mocks:ExcelSheetValidatorTestMocks = new ExcelSheetValidatorTestMocks(properties)
    template = mocks.getBlankTemplate()

  }

  /**
    * Junit tests for the EnumFloat data type
    */
  @Test
  def validateEnumFloatQualifiersTest():Unit = {
    template.dataTypes = Array("EnumFloat")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    var results:Tuple2[Boolean,String] = DecimalNumberQualifiersValidator.validateEnumDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    template.dataQualifiers(0) = ArrayBuffer("10.00")
    results = DecimalNumberQualifiersValidator.validateEnumDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * Junit tests for the RandomFloat data type
    */
  @Test
  def validateRandomFloatQualifiersTest():Unit = {
    // testing a fail when min and max are not specified
    template.dataTypes = Array("RandomFloat")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    var results:Tuple2[Boolean,String] = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing a valid value for the length format
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","-10","10")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // testing an invalid value for the length format
    template.dataQualifiers(0) = ArrayBuffer("abc","-10","10")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing a mismatch in the number of formats that need qualifiers and the number of qualifiers
    template.dataQualifiers(0) = ArrayBuffer("abc","123","-10","10")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that only one of roundup, rounddown, or roundhalf can be specified
    template.dataFormats = Array("length,min,max,roundup,roundhalf")
    template.dataQualifiers(0) = ArrayBuffer("10","123","456")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that min value is greater than max value
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","123","88")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that min value is not numeric
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","abc","88")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that max value is not numeric
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","88","abc")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that signDigits value is not numeric
    template.dataFormats = Array("length,min,max,signDigits")
    template.dataQualifiers(0) = ArrayBuffer("10","88","99","x")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
  }

  /**
    * Junit tests for the ExternalFloat data type
    */
  @Test
  def validateExternalFloatQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalFloat")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("C:\\home\\schnarbies\\output\\ssns.txt"))
    val results:Tuple2[Boolean,String] = DecimalNumberQualifiersValidator.validateExternalDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * Junit tests for the EnumDouble data type
    */
  @Test
  def validateEnumDoubleQualifiersTest():Unit = {
    template.dataTypes = Array("EnumDouble")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    var results:Tuple2[Boolean,String] = DecimalNumberQualifiersValidator.validateEnumDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    template.dataQualifiers(0) = ArrayBuffer("10.00")
    results = DecimalNumberQualifiersValidator.validateEnumDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * Junit tests for the RandomDouble data type
    */
  @Test
  def validateRandomDoubleQualifiersTest():Unit = {
    // testing a fail when min and max are not specified
    template.dataTypes = Array("RandomDouble")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    var results:Tuple2[Boolean,String] = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing a valid value for the length format
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","-10","10")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // testing an invalid value for the length format
    template.dataQualifiers(0) = ArrayBuffer("abc","-10","10")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing a mismatch in the number of formats that need qualifiers and the number of qualifiers
    template.dataQualifiers(0) = ArrayBuffer("abc","123","-10","10")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that only one of roundup, rounddown, or roundhalf can be specified
    template.dataFormats = Array("length,min,max,roundup,roundhalf")
    template.dataQualifiers(0) = ArrayBuffer("10","123","456")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that min value is greater than max value
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","123","88")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that min value is not numeric
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","abc","88")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that max value is not numeric
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","88","abc")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that signDigits value is not numeric
    template.dataFormats = Array("length,min,max,signDigits")
    template.dataQualifiers(0) = ArrayBuffer("10","88","99","x")
    results = DecimalNumberQualifiersValidator.validateRandomDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
  }

  /**
    * Junit tests for the ExternalDouble data type
    */
  @Test
  def validateExternalDoubleQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalDouble")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("C:\\home\\schnarbies\\output\\ssns.txt"))
    val results:Tuple2[Boolean,String] = DecimalNumberQualifiersValidator.validateExternalDecimalNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }
}
