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
  * class for unit testing the WholeNumberFormatValidator class methods
  */
@Test
class WholeNumberQualifiersValidatorTest {

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
    * method to test that the qualifiers for the EnumInt data type are valid
    */
  @Test
  def validateEnumIntQualifiersTest():Unit = {
    template.dataTypes = Array("EnumInt")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("something"))
    val results:Tuple2[Boolean,String] = WholeNumberQualifiersValidator.validateEnumWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * method to test that the qualifiers for the RandomInt data type are valid
    */
  @Test
  def validateRandomIntQualifiersTest():Unit = {
    // testing a fail when min and max are not specified
    template.dataTypes = Array("RandomInt")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    var results:Tuple2[Boolean,String] = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing a valid value for the length format
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","-10","10")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // testing an invalid value for the length format
    template.dataQualifiers(0) = ArrayBuffer("abc","-10","10")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing a mismatch in the number of formats that need qualifiers and the number of qualifiers
    template.dataQualifiers(0) = ArrayBuffer("abc","123","-10","10")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that min value is greater than max value
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","123","88")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that min value is not numeric
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","abc","88")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that max value is not numeric
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","88","abc")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that min value is not a whole number
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","88.5","99")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that max value is not a whole number
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","88","88.5")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
  }

  /**
    * method to test that the qualifiers for the ExternalInt data type are valid
    */
  @Test
  def validateExternalIntQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalInt")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("C:\\home\\schnarbies\\output\\ssns.txt"))
    val results:Tuple2[Boolean,String] = WholeNumberQualifiersValidator.validateExternalWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * method to test that the qualifiers for the EnumLong data type are valid
    */
  @Test
  def validateEnumLongQualifiersTest():Unit = {
    template.dataTypes = Array("EnumLong")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("something"))
    val results:Tuple2[Boolean,String] = WholeNumberQualifiersValidator.validateEnumWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * method to test that the qualifiers for the RandomLong data type are valid
    */
  @Test
  def validateRandomWholeNumberQualifiersTest():Unit = {
    // testing a fail when min and max are not specified
    template.dataTypes = Array("RandomLong")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    var results:Tuple2[Boolean,String] = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing a valid value for the length format
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","-10","10")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // testing an invalid value for the length format
    template.dataQualifiers(0) = ArrayBuffer("abc","-10","10")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing a mismatch in the number of formats that need qualifiers and the number of qualifiers
    template.dataQualifiers(0) = ArrayBuffer("abc","123","-10","10")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that min value is greater than max value
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","123","88")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that min value is not numeric
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","abc","88")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that max value is not numeric
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","88","abc")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that min value is not a whole number
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","88.5","99")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that max value is not a whole number
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","88","88.5")
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
  }

  /**
    * method to test that the qualifiers for the ExternalLong data type are valid
    */
  @Test
  def validateExternalLongQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalLong")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("C:\\home\\schnarbies\\output\\ssns.txt"))
    val results:Tuple2[Boolean,String] = WholeNumberQualifiersValidator.validateExternalWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }
}