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
  * class for unit testing the MoneyFormatValidator class methods
  */
@Test
class MoneyQualifiersValidatorTest {

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
    * Junit tests for the EnumMoney data type
    */
  @Test
  def validateEnumMoneyQualifiersTest():Unit = {
    template.dataTypes = Array("EnumMoney")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("something"))
    var results:Tuple2[Boolean,String] = MoneyQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // simple nullable test
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.1","value"))
    results = MoneyQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable not a number
    template.dataQualifiers = Array(ArrayBuffer("abc","value"))
    results = MoneyQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable greater than 1
    template.dataQualifiers = Array(ArrayBuffer("1.00001","value"))
    results = MoneyQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for negative nullable
    template.dataQualifiers = Array(ArrayBuffer("-1.00001","value"))
    results = MoneyQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1.00000","value"))
    results = MoneyQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1","value"))
    results = MoneyQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0.00000","value"))
    results = MoneyQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0","value"))
    results = MoneyQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * Junit tests for the RandomMoney data type
    */
  @Test
  def validateRandomMoneyQualifiersTest():Unit = {
    // testing a fail when min and max are not specified
    template.dataTypes = Array("RandomMoney")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    var results:Tuple2[Boolean,String] = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing a valid value for the min and max formats
    template.dataFormats = Array("min,max")
    template.dataQualifiers(0) = ArrayBuffer("-10","10")
    results = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // testing an invalid value for the min and max formats
    template.dataQualifiers(0) = ArrayBuffer("abc","10")
    results = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing a mismatch in the number of formats that need qualifiers and the number of qualifiers
    template.dataQualifiers(0) = ArrayBuffer("abc","123","-10","10")
    results = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that only one of roundup, rounddown, or roundhalf can be specified
    template.dataFormats = Array("min,max,roundup,roundhalf")
    template.dataQualifiers(0) = ArrayBuffer("123","456")
    results = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that min value is greater than max value
    template.dataFormats = Array("min,max")
    template.dataQualifiers(0) = ArrayBuffer("123","88")
    results = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // testing that max value is not numeric
    template.dataFormats = Array("min,max")
    template.dataQualifiers(0) = ArrayBuffer("88","abc")
    results = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // simple nullable test
    template.dataFormats = Array("nullable,min,max")
    template.dataQualifiers = Array(ArrayBuffer("0.1","1","10"))
    results = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable not a number
    template.dataQualifiers = Array(ArrayBuffer("abc","1","10"))
    results = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable greater than 1
    template.dataQualifiers = Array(ArrayBuffer("1.00001","1","10"))
    results = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for negative nullable
    template.dataQualifiers = Array(ArrayBuffer("-1.00001","1","10"))
    results = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1.00000","1","10"))
    results = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1","1","10"))
    results = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0.00000","1","10"))
    results = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0","1","10"))
    results = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * Junit tests for the ExternalMoney data type
    */
  @Test
  def validateExternalMoneyQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalMoney")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("C:\\home\\schnarbies\\output\\ssns.txt"))
    var results:Tuple2[Boolean,String] = MoneyQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test simple nullable
    template.dataFormats = Array("nullable")
    template.dataQualifiers(0) = ArrayBuffer("0.1","C:\\home\\schnarbies\\output\\ssns.txt")
    results = MoneyQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    println(results._2)
    // test with nullable that file path does not exist
    template.dataQualifiers(0) = ArrayBuffer("0.1","C:\\home\\schnarbies\\output\\nofile.txt")
    results = MoneyQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable not a number
    template.dataQualifiers(0) = ArrayBuffer("abc","C:\\home\\schnarbies\\output\\ssns.txt")
    results = MoneyQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable greater than 1
    template.dataQualifiers(0) = ArrayBuffer("1.0011","C:\\home\\schnarbies\\output\\ssns.txt")
    results = MoneyQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for negative nullable
    template.dataQualifiers(0) = ArrayBuffer("-1.0001","C:\\home\\schnarbies\\output\\ssns.txt")
    results = MoneyQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable = 1
    template.dataQualifiers(0) = ArrayBuffer("1.0","C:\\home\\schnarbies\\output\\ssns.txt")
    results = MoneyQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable = 1
    template.dataQualifiers(0) = ArrayBuffer("1","C:\\home\\schnarbies\\output\\ssns.txt")
    results = MoneyQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable = 0
    template.dataQualifiers(0) = ArrayBuffer("0.0","C:\\home\\schnarbies\\output\\ssns.txt")
    results = MoneyQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable = 0
    template.dataQualifiers(0) = ArrayBuffer("0","C:\\home\\schnarbies\\output\\ssns.txt")
    results = MoneyQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }
}
