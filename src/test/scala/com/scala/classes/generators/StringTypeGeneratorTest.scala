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
  * this object generates each of the different String types
  */
@Test
class StringTypeGeneratorTest {

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
  def makeEnumStringTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("string1","string2","string3"))
    var results:String = StringTypeGenerator.makeEnumString(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(StringTypeGenerator.makeEnumString(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RandomString data type
    */
  @Test
  def makeRandomStringTest():Unit = {
    // simple normal test with only length specified
    template.dataFormats = Array("length")
    template.dataQualifiers(0) = ArrayBuffer("10")
    var results:String = StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with length and chars
    template.dataFormats = Array("length,chars")
    template.dataQualifiers(0) = ArrayBuffer("10","abc")
    results = StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test multiples to make sure all range of characters are getting selected
    for(i <- 0 until 30) {
      println(StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with length and chars and upper
    template.dataFormats = Array("length,chars,upper")
    template.dataQualifiers(0) = ArrayBuffer("10","AbCdEfGhIjKlMnOp1234567ZzYyXwVu")
    results = StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with length and chars and lower
    template.dataFormats = Array("length,chars,lower")
    template.dataQualifiers(0) = ArrayBuffer("10","AbCdEfGhIjKlMnOp1234567ZzYyXwVu")
    results = StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with length and chars and upper and lower
    template.dataFormats = Array("length,chars,upper,lower")
    template.dataQualifiers(0) = ArrayBuffer("10","AbCdEfGhIjKlMnOp1234567ZzYyXwVu")
    results = StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0)))
    }
    template.dataFormats = Array("NONE")
    template.dataQualifiers(0) = ArrayBuffer()
    results = StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the ExternalString data type
    */
  @Test
  def makeExternalStringTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("string1","string2","string3"))
    var results:String = StringTypeGenerator.makeExternalString(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(StringTypeGenerator.makeExternalString(template.dataQualifiers(0)))
    }
  }
}
