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
import org.junit.Assert.assertNotNull
import org.junit.{Before, Test}

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different Money types
  */
@Test
class MoneyTypeGeneratorTest {

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
    * Junit tests for the EnumMoney data type
    */
  @Test
  def makeEnumMoneyTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = MoneyTypeGenerator.makeEnumMoney(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeEnumMoney(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RandomMoney data type
    */
  @Test
  def makeRandomMoneyTest():Unit = {
    // test with length and min and max
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","10","100")
    var results:String = MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test sginificant digits
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","10","1000000")
    results = MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","-10000","0")
    results = MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test rounding
    template.dataFormats = Array("length,min,max,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("10","10","1000000")
    results = MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with no padding
    template.dataFormats = Array("min,max,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("-10","10")
    results = MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with no padding
    template.dataFormats = Array("min,max,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("-10","10")
    results = MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with padding
    template.dataFormats = Array("length,min,max,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("6","-10","10")
    results = MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the ExternalMoney data type
    */
  @Test
  def makeExternalMoneyTest():Unit = {
    // simple test
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = MoneyTypeGenerator.makeExternalMoney(template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeExternalMoney(template.dataQualifiers(0)))
    }
  }

  /**
    * Junit tests for the RangedMoney data type
    */
  @Test
  def makeRangedMoneyTest():Unit = {
    // test with length and min and max
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","10","100")
    var results:String = MoneyTypeGenerator.makeRangedMoney(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRangedMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test sginificant digits
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","10","1000000")
    results = MoneyTypeGenerator.makeRangedMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRangedMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives
    template.dataFormats = Array("length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","-10000","0")
    results = MoneyTypeGenerator.makeRangedMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRangedMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test rounding
    template.dataFormats = Array("length,min,max,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("10","10","1000000")
    results = MoneyTypeGenerator.makeRangedMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRangedMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with no padding
    template.dataFormats = Array("min,max,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("-10","10")
    results = MoneyTypeGenerator.makeRangedMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRangedMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with no padding
    template.dataFormats = Array("min,max,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("-10","10")
    results = MoneyTypeGenerator.makeRangedMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRangedMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatvies and positives, with padding
    template.dataFormats = Array("length,min,max,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("6","-10","10")
    results = MoneyTypeGenerator.makeRangedMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRangedMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
  }
}
