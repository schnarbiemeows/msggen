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
  *
  */
@Test
class DoubleTypeGeneratorTest {

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
    * Junit tests for the EnumDouble data type
    */
  @Test
  def makeEnumDoubleTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = DoubleTypeGenerator.makeEnumDouble(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeEnumDouble(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RandomDouble data type
    */
  @Test
  def makeRandomDoubleTest():Unit = {
    // test with length and min and max
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","10","100")
    var results:String = DoubleTypeGenerator.makeRandomDouble(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeRandomDouble(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test sginificant digits
    template.dataFormats = Array("length,min,max,signDigits")
    template.dataQualifiers(0) = ArrayBuffer("10","10","1000000","2")
    results = DoubleTypeGenerator.makeRandomDouble(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeRandomDouble(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives
    template.dataFormats = Array("length,min,max,signDigits")
    template.dataQualifiers(0) = ArrayBuffer("10","-10000","0","2")
    results = DoubleTypeGenerator.makeRandomDouble(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeRandomDouble(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test rounding
    template.dataFormats = Array("length,min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("10","10","1000000","15")
    results = DoubleTypeGenerator.makeRandomDouble(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeRandomDouble(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with no padding
    template.dataFormats = Array("min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("-10","10","15")
    results = DoubleTypeGenerator.makeRandomDouble(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeRandomDouble(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with no padding
    template.dataFormats = Array("min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("-10","10","2")
    results = DoubleTypeGenerator.makeRandomDouble(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeRandomDouble(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with padding
    template.dataFormats = Array("length,min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("6","-10","10","2")
    results = DoubleTypeGenerator.makeRandomDouble(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeRandomDouble(template.dataFormats(0),template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the ExternalDouble data type
    */
  @Test
  def makeExternalDoubleTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = DoubleTypeGenerator.makeExternalDouble(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeExternalDouble(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RangedDouble data type
    */
  @Test
  def makeRangedDoubleTest():Unit = {
    // test with length and min and max
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","10","100")
    var results:String = DoubleTypeGenerator.makeRangedDouble(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeRangedDouble(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test sginificant digits
    template.dataFormats = Array("length,min,max,signDigits")
    template.dataQualifiers(0) = ArrayBuffer("10","10","1000000","2")
    results = DoubleTypeGenerator.makeRangedDouble(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeRangedDouble(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives
    template.dataFormats = Array("length,min,max,signDigits")
    template.dataQualifiers(0) = ArrayBuffer("10","-10000","0","2")
    results = DoubleTypeGenerator.makeRangedDouble(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeRangedDouble(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test rounding
    template.dataFormats = Array("length,min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("10","10","1000000","15")
    results = DoubleTypeGenerator.makeRangedDouble(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeRangedDouble(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with no padding
    template.dataFormats = Array("min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("-10","10","15")
    results = DoubleTypeGenerator.makeRangedDouble(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeRangedDouble(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with no padding
    template.dataFormats = Array("min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("-10","10","2")
    results = DoubleTypeGenerator.makeRangedDouble(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeRangedDouble(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with padding
    template.dataFormats = Array("length,min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("6","-10","10","2")
    results = DoubleTypeGenerator.makeRangedDouble(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(DoubleTypeGenerator.makeRangedDouble(template.dataFormats(0),template.dataQualifiers(0)))
    }
  }

}
