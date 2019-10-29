/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import com.scala.classes.posos.GenericRecordsTemplate
import org.junit.Assert._
import org.junit.{Before, Test}

import scala.collection.mutable.ArrayBuffer

/**
  * class for unit testing the WholeNumberFormatValidator class methods
  */
@Test
class WholeNumberQualifiersValidatorTest {

  var template:GenericRecordsTemplate = new ExcelSheetValidatorTestMocks().getBlankTemplate()

  /**
    * initialization
    */
  @Before
  def initialize():Unit = {
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
    var results:Tuple2[Boolean,String] = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // simple nullable test
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.1","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable not a number
    template.dataQualifiers = Array(ArrayBuffer("abc","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable greater than 1
    template.dataQualifiers = Array(ArrayBuffer("1.00001","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for negative nullable
    template.dataQualifiers = Array(ArrayBuffer("-1.00001","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1.00000","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0.00000","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
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
    // simple nullable test
    template.dataFormats = Array("nullable,min,max")
    template.dataQualifiers = Array(ArrayBuffer("0.1","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable not a number
    template.dataQualifiers = Array(ArrayBuffer("abc","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable greater than 1
    template.dataQualifiers = Array(ArrayBuffer("1.00001","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for negative nullable
    template.dataQualifiers = Array(ArrayBuffer("-1.00001","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1.00000","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0.00000","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  @Test
  def validateRangedIntQualifiersTest(): Unit = {
    // simple test
    template.dataTypes = Array("RangedInt")
    template.fields = Array("field1")
    template.dataFormats = Array("linbase,linadd")
    template.dataQualifiers = Array(ArrayBuffer("0","1"))
    var results:Tuple2[Boolean,String] = WholeNumberQualifiersValidator.validateRangedWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    println(results._2)
    // test where you forget linadd
    template.dataFormats = Array("linbase")
    template.dataQualifiers = Array(ArrayBuffer("0"))
    results = WholeNumberQualifiersValidator.validateRangedWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test where you forget linbase
    template.dataFormats = Array("linadd")
    template.dataQualifiers = Array(ArrayBuffer("0"))
    results = WholeNumberQualifiersValidator.validateRangedWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test where you don't specify a number
    template.dataFormats = Array("linadd,linbase")
    template.dataQualifiers = Array(ArrayBuffer("0","a"))
    results = WholeNumberQualifiersValidator.validateRangedWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test where you don't specify a number
    template.dataFormats = Array("linadd,linbase")
    template.dataQualifiers = Array(ArrayBuffer("a","10"))
    results = WholeNumberQualifiersValidator.validateRangedWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test where you don't specify a whole number
    template.dataFormats = Array("linadd,linbase")
    template.dataQualifiers = Array(ArrayBuffer("1.2","10"))
    results = WholeNumberQualifiersValidator.validateRangedWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test where you specify a negative whole number
    template.dataFormats = Array("linadd,linbase")
    template.dataQualifiers = Array(ArrayBuffer("-1","10"))
    results = WholeNumberQualifiersValidator.validateRangedWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
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
    var results:Tuple2[Boolean,String] = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // simple nullable test
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.1","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test with nullable that file path does not exist
    template.dataQualifiers(0) = ArrayBuffer("0.1","C:\\home\\schnarbies\\output\\nofile.txt")
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable not a number
    template.dataQualifiers = Array(ArrayBuffer("abc","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable greater than 1
    template.dataQualifiers = Array(ArrayBuffer("1.00001","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for negative nullable
    template.dataQualifiers = Array(ArrayBuffer("-1.00001","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1.00000","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0.00000","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
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
    var results:Tuple2[Boolean,String] = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // simple nullable test
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.1","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable not a number
    template.dataQualifiers = Array(ArrayBuffer("abc","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable greater than 1
    template.dataQualifiers = Array(ArrayBuffer("1.00001","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for negative nullable
    template.dataQualifiers = Array(ArrayBuffer("-1.00001","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1.00000","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0.00000","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0","1"))
    results = WholeNumberQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * method to test that the qualifiers for the RandomLong data type are valid
    */
  @Test
  def validateRandomLongQualifiersTest():Unit = {
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
    // simple nullable test
    template.dataFormats = Array("nullable,min,max")
    template.dataQualifiers = Array(ArrayBuffer("0.1","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable not a number
    template.dataQualifiers = Array(ArrayBuffer("abc","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable greater than 1
    template.dataQualifiers = Array(ArrayBuffer("1.00001","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for negative nullable
    template.dataQualifiers = Array(ArrayBuffer("-1.00001","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1.00000","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0.00000","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0","1","10"))
    results = WholeNumberQualifiersValidator.validateRandomWholeNumberQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
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
    var results:Tuple2[Boolean,String] = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // simple nullable test
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.1","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test with nullable that file path does not exist
    template.dataQualifiers(0) = ArrayBuffer("0.1","C:\\home\\schnarbies\\output\\nofile.txt")
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable not a number
    template.dataQualifiers = Array(ArrayBuffer("abc","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable greater than 1
    template.dataQualifiers = Array(ArrayBuffer("1.00001","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for negative nullable
    template.dataQualifiers = Array(ArrayBuffer("-1.00001","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1.00000","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0.00000","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0","C:\\home\\schnarbies\\output\\ssns.txt"))
    results = WholeNumberQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }
}
