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
  * this object generates each of the different Int types
  */
@Test
class IntTypeGeneratorTest {

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
    * Junit tests for the EnumInt data type
    */
  @Test
  def makeEnumIntTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = IntTypeGenerator.makeEnumInt(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(IntTypeGenerator.makeEnumInt(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RandomInt data type
    */
  @Test
  def makeRandomIntTest():Unit = {
    // test with length and min and max
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","10","100")
    var results:String = IntTypeGenerator.makeRandomInt(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(IntTypeGenerator.makeRandomInt(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives
    template.dataQualifiers(0) = ArrayBuffer("10","-19","-5")
    results = IntTypeGenerator.makeRandomInt(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(IntTypeGenerator.makeRandomInt(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives and positives
    template.dataQualifiers(0) = ArrayBuffer("10","-9","90")
    results = IntTypeGenerator.makeRandomInt(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(IntTypeGenerator.makeRandomInt(template.dataFormats(0),template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the ExternalInt data type
    */
  @Test
  def makeExternalIntTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = IntTypeGenerator.makeExternalInt(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(IntTypeGenerator.makeExternalInt(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RangedInt data type
    */
  @Test
  def makeRangedIntTest():Unit = {
    // test with length and min and max
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","10","100")
    var results:String = IntTypeGenerator.makeRangedInt(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(IntTypeGenerator.makeRangedInt(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives
    template.dataQualifiers(0) = ArrayBuffer("10","-19","-5")
    results = IntTypeGenerator.makeRangedInt(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(IntTypeGenerator.makeRangedInt(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives and positives
    template.dataQualifiers(0) = ArrayBuffer("10","-9","90")
    results = IntTypeGenerator.makeRangedInt(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(IntTypeGenerator.makeRangedInt(template.dataFormats(0),template.dataQualifiers(0)))
    }
  }
}
