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
import org.junit.Assert.assertNotNull
import org.junit.{Before, Test}

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different LocalDate types
  */
@Test
class DateTypeGeneratorTest {

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
  def makeEnumDateTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("date1","date2","date3"))
    var results:String = DateTypeGenerator.makeEnumDate(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(DateTypeGenerator.makeEnumDate(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RandomDateTime data type
    */
  @Test
  def makeRandomDateTest():Unit = {
    // simple normal test
    template.dataFormats = Array("format,end,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2019-10-10","2018-10-10")
    var results:String = DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 100) {
      println(DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with specifying no start date
    template.dataFormats = Array("format,end")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2019-10-10")
    results = DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying no end date
    template.dataFormats = Array("format,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2000-10-10")
    results = DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying no start date or end date
    template.dataFormats = Array("format")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd")
    results = DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
  }

  /**
    * Junit tests for the ExternalDate data type
    */
  @Test
  def makeExternalDateTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("date1","date2","date3"))
    var results:String = DateTypeGenerator.makeExternalDate(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(DateTypeGenerator.makeExternalDate(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RangedDate data type
    */
  @Test
  def makeRangeDateTest():Unit = {
    // simple normal test
    template.dataFormats = Array("format,end,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2019-10-10","2018-10-10")
    var results:String = DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 100) {
      println(DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with specifying no start date
    template.dataFormats = Array("format,end")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2019-10-10")
    results = DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying no end date
    template.dataFormats = Array("format,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2000-10-10")
    results = DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying no start date or end date
    template.dataFormats = Array("format")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd")
    results = DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
  }
}
