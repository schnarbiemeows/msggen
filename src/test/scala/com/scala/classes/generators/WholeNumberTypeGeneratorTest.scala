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
class WholeNumberTypeGeneratorTest {

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
    * Junit tests for the EnumLong data type
    */
  @Test
  def makeEnumWholeNumberTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = WholeNumberTypeGenerator.makeEnumWholeNumber(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(WholeNumberTypeGenerator.makeEnumWholeNumber(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RandomLong data type
    */
  @Test
  def makeRandomWholeNumberTest():Unit = {
    // test with length and min and max
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","10","100")
    var results:String = WholeNumberTypeGenerator.makeRandomWholeNumber(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(WholeNumberTypeGenerator.makeRandomWholeNumber(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives
    template.dataQualifiers(0) = ArrayBuffer("10","-19","-5")
    results = WholeNumberTypeGenerator.makeRandomWholeNumber(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(WholeNumberTypeGenerator.makeRandomWholeNumber(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives and positives
    template.dataQualifiers(0) = ArrayBuffer("10","-9","90")
    results = WholeNumberTypeGenerator.makeRandomWholeNumber(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(WholeNumberTypeGenerator.makeRandomWholeNumber(template.dataFormats(0),template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the ExternalLong data type
    */
  @Test
  def makeExternalWholeNumberTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = WholeNumberTypeGenerator.makeExternalWholeNumber(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(WholeNumberTypeGenerator.makeExternalWholeNumber(template.dataQualifiers(0)))
    }
  }
}
