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
class FloatTypeGeneratorTest {

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
    * Junit tests for the EnumFloat data type
    */
  @Test
  def makeEnumFloatTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = FloatTypeGenerator.makeEnumFloat(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeEnumFloat(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RandomFloat data type
    */
  @Test
  def makeRandomFloatTest():Unit = {
    // test with length and min and max
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","10","100")
    var results:String = FloatTypeGenerator.makeRandomFloat(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeRandomFloat(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test sginificant digits
    template.dataFormats = Array("length,min,max,signDigits")
    template.dataQualifiers(0) = ArrayBuffer("10","10","1000000","2")
    results = FloatTypeGenerator.makeRandomFloat(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeRandomFloat(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives
    template.dataFormats = Array("length,min,max,signDigits")
    template.dataQualifiers(0) = ArrayBuffer("10","-10000","0","2")
    results = FloatTypeGenerator.makeRandomFloat(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeRandomFloat(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test rounding
    template.dataFormats = Array("length,min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("10","10","1000000","15")
    results = FloatTypeGenerator.makeRandomFloat(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeRandomFloat(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with no padding
    template.dataFormats = Array("min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("-10","10","15")
    results = FloatTypeGenerator.makeRandomFloat(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeRandomFloat(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with no padding
    template.dataFormats = Array("min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("-10","10","2")
    results = FloatTypeGenerator.makeRandomFloat(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeRandomFloat(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with padding
    template.dataFormats = Array("length,min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("6","-10","10","2")
    results = FloatTypeGenerator.makeRandomFloat(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeRandomFloat(template.dataFormats(0),template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the ExternalFloat data type
    */
  @Test
  def makeExternalFloatTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = FloatTypeGenerator.makeExternalFloat(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeExternalFloat(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RangedFloat data type
    */
  @Test
  def makeRangedFloatTest():Unit = {
    // test with length and min and max
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","10","100")
    var results:String = FloatTypeGenerator.makeRangedFloat(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeRangedFloat(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test sginificant digits
    template.dataFormats = Array("length,min,max,signDigits")
    template.dataQualifiers(0) = ArrayBuffer("10","10","1000000","2")
    results = FloatTypeGenerator.makeRangedFloat(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeRangedFloat(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives
    template.dataFormats = Array("length,min,max,signDigits")
    template.dataQualifiers(0) = ArrayBuffer("10","-10000","0","2")
    results = FloatTypeGenerator.makeRangedFloat(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeRangedFloat(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test rounding
    template.dataFormats = Array("length,min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("10","10","1000000","15")
    results = FloatTypeGenerator.makeRangedFloat(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeRangedFloat(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with no padding
    template.dataFormats = Array("min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("-10","10","15")
    results = FloatTypeGenerator.makeRangedFloat(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeRangedFloat(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with no padding
    template.dataFormats = Array("min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("-10","10","2")
    results = FloatTypeGenerator.makeRangedFloat(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeRangedFloat(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with padding
    template.dataFormats = Array("length,min,max,signDigits,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("6","-10","10","2")
    results = FloatTypeGenerator.makeRangedFloat(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(FloatTypeGenerator.makeRangedFloat(template.dataFormats(0),template.dataQualifiers(0)))
    }
  }

}
