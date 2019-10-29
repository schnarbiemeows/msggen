/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.validators.ExcelSheetValidatorTestMocks
import org.junit.Assert.assertNotNull
import org.junit.{Before, Test}

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different String types
  */
@Test
class StringTypeGeneratorTest {

  var template:GenericRecordsTemplate = new ExcelSheetValidatorTestMocks().getBlankTemplate()

  /**
    * initialization
    */
  @Before
  def initialize():Unit = {
  }

  /**
    * Junit tests for the EnumString data type
    */
  @Test
  def makeEnumStringTest():Unit = {
    // simple test
    val dataType:String = "EnumString"
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("string1","string2","string3"))
    var results:String = StringTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0), template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.5","string1","string2","string3"))
    results = StringTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0), template.dataQualifiers(0))
    println("50% nullable")
    for(i <- 0 until 20) {
      println(StringTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0","string1","string2","string3"))
    for(i <- 0 until 20) {
      println(StringTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0","string1","string2","string3"))
    for(i <- 0 until 20) {
      println(StringTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
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
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.5"))
    results = StringTypeGenerator.makeRandomString(template.dataFormats(0), template.dataQualifiers(0))
    println("50% nullable")
    for(i <- 0 until 20) {
      println(StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0"))
    for(i <- 0 until 20) {
      println(StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0"))
    for(i <- 0 until 20) {
      println(StringTypeGenerator.makeRandomString(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
  }

  /**
    * Junit tests for the ExternalString data type
    */
  @Test
  def makeExternalStringTest():Unit = {
    // simple test
    val dataType:String = "ExternalString"
    template.dataFormats = Array("None")
    template.dataQualifiers = Array(ArrayBuffer("string1","string2","string3"))
    var results:String = StringTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.5","string1","string2","string3"))
    results = StringTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0), template.dataQualifiers(0))
    println("50% nullable")
    for(i <- 0 until 20) {
      println(StringTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0","string1","string2","string3"))
    for(i <- 0 until 20) {
      println(StringTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0","string1","string2","string3"))
    for(i <- 0 until 20) {
      println(StringTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
  }
}
