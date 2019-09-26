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
  * this object generates each of the different Long types
  */
@Test
class LongTypeGeneratorTest {

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
    * Junit tests for the EnumLong data type
    */
  @Test
  def makeEnumLongTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = LongTypeGenerator.makeEnumLong(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(LongTypeGenerator.makeEnumLong(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RandomLong data type
    */
  @Test
  def makeRandomLongTest():Unit = {
    // test with length and min and max
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","10","100")
    var results:String = LongTypeGenerator.makeRandomLong(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(LongTypeGenerator.makeRandomLong(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives
    template.dataQualifiers(0) = ArrayBuffer("10","-19","-5")
    results = LongTypeGenerator.makeRandomLong(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(LongTypeGenerator.makeRandomLong(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives and positives
    template.dataQualifiers(0) = ArrayBuffer("10","-9","90")
    results = LongTypeGenerator.makeRandomLong(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(LongTypeGenerator.makeRandomLong(template.dataFormats(0),template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the ExternalLong data type
    */
  @Test
  def makeExternalLongTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = LongTypeGenerator.makeExternalLong(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(LongTypeGenerator.makeExternalLong(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RangedLong data type
    */
  @Test
  def makeRangedLongTest():Unit = {
    // test with length and min and max
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","10","100")
    var results:String = LongTypeGenerator.makeRangedLong(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(LongTypeGenerator.makeRangedLong(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives
    template.dataQualifiers(0) = ArrayBuffer("10","-19","-5")
    results = LongTypeGenerator.makeRangedLong(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(LongTypeGenerator.makeRangedLong(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives and positives
    template.dataQualifiers(0) = ArrayBuffer("10","-9","90")
    results = LongTypeGenerator.makeRangedLong(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(LongTypeGenerator.makeRangedLong(template.dataFormats(0),template.dataQualifiers(0)))
    }
  }
}
