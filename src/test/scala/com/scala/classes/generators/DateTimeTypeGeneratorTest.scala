/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import java.util.Properties

import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.utilities.PropertyLoader
import com.scala.classes.validators.ExcelSheetValidatorTestMocks
import org.junit.Assert._
import org.junit.{Before, Test}

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different LocalDateTime types
  */
@Test
class DateTimeTypeGeneratorTest {

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
    * Junit tests for the EnumDateTime data type
    */
  @Test
  def makeEnumDateTimeTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("date1","date2","date3"))
    var results:String = DateTimeTypeGenerator.makeEnumDateTime(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(DateTimeTypeGenerator.makeEnumDateTime(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RandomDateTime data type
    */
  @Test
  def makeRandomDateTimeTest():Unit = {
    // simple normal test
    template.dataFormats = Array("format,end,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss","2019-10-10 00:00:00","2018-10-10 00:00:00")
    var results:String = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 1) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with short range to see if the edge cases are beng handled
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH","2018-10-15 12","1900-10-10 02")
    for(i <- 0 until 1) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with specifying no start date
    template.dataFormats = Array("format,end")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm","2019-10-10 00:00")
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 1) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with specifying no end date
    template.dataFormats = Array("format,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss","2000-01-01 00:00:00")
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 1) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with specifying no start date or end date
    template.dataFormats = Array("format")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss")
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 1) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    template.dataFormats = Array("NONE")
    template.dataQualifiers(0) = ArrayBuffer()
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying now with a format, but no start date or end date
    template.dataFormats = Array("format,now")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss")
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying now with no format or start/end date
    template.dataFormats = Array("now")
    template.dataQualifiers(0) = ArrayBuffer()
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
  }

  /**
    * Junit tests for the ExternalDateTime data type
    */
  @Test
  def makeExternalDateTimeTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("date1","date2","date3"))
    var results:String = DateTimeTypeGenerator.makeExternalDateTime(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(DateTimeTypeGenerator.makeExternalDateTime(template.dataQualifiers(0)))
    }
  }
}
