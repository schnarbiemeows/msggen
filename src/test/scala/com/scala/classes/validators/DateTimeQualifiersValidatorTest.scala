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
    * initialization
    */
  @Before
  def initialize():Unit = {
    properties = PropertyLoader.getProperties("C:\\home\\schnarbies\\config\\config.properties")
    var mocks:ExcelSheetValidatorTestMocks = new ExcelSheetValidatorTestMocks(properties)
    template = mocks.getBlankTemplate()

  }

  /**
    * Junit tests for the EnumDate data type
    */
  @Test
  def validateEnumDateQualifiersTest():Unit = {
    template.dataTypes = Array("EnumDate")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("something"))
    var results:Tuple2[Boolean,String] = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // simple nullable test
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.1","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable not a number
    template.dataQualifiers = Array(ArrayBuffer("abc","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable greater than 1
    template.dataQualifiers = Array(ArrayBuffer("1.00001","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for negative nullable
    template.dataQualifiers = Array(ArrayBuffer("-1.00001","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1.00000","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0.00000","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * Junit tests for the RandomDate data type
    */
  @Test
  def validateRandomDateQualifiersTest():Unit = {
    template.dataTypes = Array("RandomDate")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    var results:Tuple2[Boolean,String] = DateTimeQualifiersValidator.validateRandomDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test with specifying all valid formats with valid qualifiers
    template.dataFormats = Array("format,end,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2019-10-10","2018-10-10")
    results = DateTimeQualifiersValidator.validateRandomDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test with specifying an invalid end date
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","XXXX-10-10","2018-10-10")
    results = DateTimeQualifiersValidator.validateRandomDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test with specifying an invalid start date
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2019-10-10","YYYY-10-10")
    results = DateTimeQualifiersValidator.validateRandomDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test with specifying a end date that comes before the start date
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2018-10-10","2019-10-10")
    results = DateTimeQualifiersValidator.validateRandomDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test with unequal formats/qualifiers lengths
    template.dataFormats = Array("format")
    results = DateTimeQualifiersValidator.validateRandomDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test that start and end dates do not need to be specified
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd")
    results = DateTimeQualifiersValidator.validateRandomDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test that start date only can be specified
    template.dataFormats = Array("format,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2018-10-10")
    results = DateTimeQualifiersValidator.validateRandomDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test that end date only can be specified
    template.dataFormats = Array("format,end")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2018-10-10")
    results = DateTimeQualifiersValidator.validateRandomDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test that the now format is valid
    template.dataFormats = Array("format,end,now")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2018-10-10")
    results = DateTimeQualifiersValidator.validateRandomDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)

    template.dataFormats = Array("now")
    template.dataQualifiers(0) = ArrayBuffer()
    results = DateTimeQualifiersValidator.validateRandomDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)

    template.dataFormats = Array("now,format")
    template.dataQualifiers(0) = ArrayBuffer("yyyyMMdd")
    results = DateTimeQualifiersValidator.validateRandomDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)

    template.dataFormats = Array("format,now")
    template.dataQualifiers(0) = ArrayBuffer("MM/dd/yyyy")
    results = DateTimeQualifiersValidator.validateRandomDateQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * Junit tests for the ExternalDate data type
    */
  @Test
  def validateExternalDateQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalDate")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("C:\\home\\schnarbies\\output\\ssns.txt"))
    var results:Tuple2[Boolean,String] = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test simple nullable
    template.dataFormats = Array("nullable")
    template.dataQualifiers(0) = ArrayBuffer("0.1","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    println(results._2)
    // test with nullable that file path does not exist
    template.dataQualifiers(0) = ArrayBuffer("0.1","C:\\home\\schnarbies\\output\\nofile.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable not a number
    template.dataQualifiers(0) = ArrayBuffer("abc","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable greater than 1
    template.dataQualifiers(0) = ArrayBuffer("1.0011","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for negative nullable
    template.dataQualifiers(0) = ArrayBuffer("-1.0001","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable = 1
    template.dataQualifiers(0) = ArrayBuffer("1.0","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable = 1
    template.dataQualifiers(0) = ArrayBuffer("1","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable = 0
    template.dataQualifiers(0) = ArrayBuffer("0.0","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable = 0
    template.dataQualifiers(0) = ArrayBuffer("0","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * Junit tests for the EnumDateTime data type
    */
  @Test
  def validateEnumDateTimeQualifiersTest():Unit = {
    template.dataTypes = Array("EnumDateTime")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("something"))
    var results:Tuple2[Boolean,String] = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // simple nullable test
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.1","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable not a number
    template.dataQualifiers = Array(ArrayBuffer("abc","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable greater than 1
    template.dataQualifiers = Array(ArrayBuffer("1.00001","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for negative nullable
    template.dataQualifiers = Array(ArrayBuffer("-1.00001","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1.00000","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 1
    template.dataQualifiers = Array(ArrayBuffer("1","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0.00000","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable equal to 0
    template.dataQualifiers = Array(ArrayBuffer("0","value"))
    results = DateTimeQualifiersValidator.validateEnumTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * Junit tests for the RandomDateTime data type
    */
  @Test
  def validateRandomDateTimeQualifiersTest():Unit = {
    template.dataTypes = Array("RandomDateTime")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    var results:Tuple2[Boolean,String] = DateTimeQualifiersValidator.validateRandomDateTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test with specifying all valid formats with valid qualifiers
    template.dataFormats = Array("format,end,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss","2019-10-10 00:00:00","2018-10-10 00:00:00")
    results = DateTimeQualifiersValidator.validateRandomDateTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test with specifying an invalid end date
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss","XXXX-10-10 00:00:00","2018-10-10 00:00:00")
    results = DateTimeQualifiersValidator.validateRandomDateTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test with specifying an invalid start date
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss","2019-10-10 00:00:00","YYYY-10-10 00:00:00")
    results = DateTimeQualifiersValidator.validateRandomDateTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test with specifying a end date that comes before the start date
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss","2018-10-10 00:00:00","2019-10-10 00:00:00")
    results = DateTimeQualifiersValidator.validateRandomDateTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test with unequal formats/qualifiers lengths
    template.dataFormats = Array("format")
    results = DateTimeQualifiersValidator.validateRandomDateTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test that start and end dates do not need to be specified
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss")
    results = DateTimeQualifiersValidator.validateRandomDateTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test that start date only can be specified
    template.dataFormats = Array("format,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss","2018-10-10 00:00:00")
    results = DateTimeQualifiersValidator.validateRandomDateTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test that end date only can be specified
    template.dataFormats = Array("format,end")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss","2018-10-10 00:00:00")
    results = DateTimeQualifiersValidator.validateRandomDateTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test that the now format won't mess anything up
    template.dataFormats = Array("format,end,now")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss","2018-10-10 00:00:00")
    results = DateTimeQualifiersValidator.validateRandomDateTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)

    template.dataFormats = Array("format,start,end")
    template.dataQualifiers(0) = ArrayBuffer("dd/MM/yyyy HH:mm","01/01/1900 00:00","31/12/2000 00:00")
    results = DateTimeQualifiersValidator.validateRandomDateTimeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    * Junit tests for the ExternalDateTime data type
    */
  @Test
  def validateExternalDateTimeQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalDateTime")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("C:\\home\\schnarbies\\output\\ssns.txt"))
    var results:Tuple2[Boolean,String] = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test simple nullable
    template.dataFormats = Array("nullable")
    template.dataQualifiers(0) = ArrayBuffer("0.1","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    println(results._2)
    // test with nullable that file path does not exist
    template.dataQualifiers(0) = ArrayBuffer("0.1","C:\\home\\schnarbies\\output\\nofile.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable not a number
    template.dataQualifiers(0) = ArrayBuffer("abc","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable greater than 1
    template.dataQualifiers(0) = ArrayBuffer("1.0011","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for negative nullable
    template.dataQualifiers(0) = ArrayBuffer("-1.0001","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertFalse(results._1)
    println(results._2)
    // test for nullable = 1
    template.dataQualifiers(0) = ArrayBuffer("1.0","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable = 1
    template.dataQualifiers(0) = ArrayBuffer("1","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable = 0
    template.dataQualifiers(0) = ArrayBuffer("0.0","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
    // test for nullable = 0
    template.dataQualifiers(0) = ArrayBuffer("0","C:\\home\\schnarbies\\output\\ssns.txt")
    results = DateTimeQualifiersValidator.validateExternalTypeQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

}
