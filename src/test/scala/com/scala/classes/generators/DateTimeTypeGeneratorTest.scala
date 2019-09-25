/*
 * Created 2019 by Dylan Kessler
 */

/*
 * Created 2019 by Dylan Kessler
 */

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
    properties = PropertyLoader.getProperties("config.properties")
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
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2019-10-10","2018-10-10")
    var results:String = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with short range to see if the edge cases are beng handled
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2018-10-15","2018-10-10")
    for(i <- 0 until 20) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with specifying no start date
    template.dataFormats = Array("format,end")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2019-10-10")
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying no end date
    template.dataFormats = Array("format,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2000-10-10")
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying no start date or end date
    template.dataFormats = Array("format")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd")
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

  /**
    * Junit tests for the RangedDateTime data type
    */
  @Test
  def makeRangeDateTimeTest():Unit = {
    // simple normal test
    template.dataFormats = Array("format,end,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2019-10-10","2018-10-10")
    var results:String = DateTimeTypeGenerator.makeRangedDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 100) {
      println(DateTimeTypeGenerator.makeRangedDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with specifying no start date
    template.dataFormats = Array("format,end")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2019-10-10")
    results = DateTimeTypeGenerator.makeRangedDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying no end date
    template.dataFormats = Array("format,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2000-10-10")
    results = DateTimeTypeGenerator.makeRangedDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying no start date or end date
    template.dataFormats = Array("format")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd")
    results = DateTimeTypeGenerator.makeRangedDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
  }
}
