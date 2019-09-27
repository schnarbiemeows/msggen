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
  * class for unit testing the StringQualifiersValidator class methods
  */
@Test
class StringQualifiersValidatorTest {

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
    * Junit tests for the EnumString data type
    */
  @Test
  def validateEnumStringQualifiersTest():Unit = {
    template.dataTypes = Array("EnumString")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("something"))
    val results:Tuple2[Boolean,String] = StringQualifiersValidator.validateEnumStringQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * Junit tests for the RandomString data type
    */
  @Test
  def validateRandomStringQualifiersTest():Unit = {
    template.dataTypes = Array("RandomString")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    var results:Tuple2[Boolean,String] = StringQualifiersValidator.validateRandomStringQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // testing a valid value for the length format
    template.dataFormats = Array("length")
    template.dataQualifiers(0) = ArrayBuffer("10")
    results = StringQualifiersValidator.validateRandomStringQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    println(results._2)
    // testing an invalid value for the length format
    template.dataQualifiers(0) = ArrayBuffer("abc")
    results = StringQualifiersValidator.validateRandomStringQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing a mismatch in the number of formats that need qualifiers and the number of qualifiers
    template.dataQualifiers(0) = ArrayBuffer("abc","123")
    results = StringQualifiersValidator.validateRandomStringQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that the indexing works when iterating through qualifiers
    template.dataFormats = Array("length,chars")
    template.dataQualifiers(0) = ArrayBuffer("abc","123")
    results = StringQualifiersValidator.validateRandomStringQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    println(results._2)
  }

  /**
    * Junit tests for the RandomString data type
    */
  @Test
  def validateExternalStringQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalString")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("C:\\home\\schnarbies\\output\\ssns.txt"))
    val results:Tuple2[Boolean,String] = StringQualifiersValidator.validateExternalStringQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    println(results._2)
    assertTrue(results._1)
  }

}
