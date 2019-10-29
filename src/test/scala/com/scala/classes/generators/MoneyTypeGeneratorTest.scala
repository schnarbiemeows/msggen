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
  * this object generates each of the different Money types
  */
@Test
class MoneyTypeGeneratorTest {

  var template:GenericRecordsTemplate = new ExcelSheetValidatorTestMocks().getBlankTemplate()

  /**
    * initialization
    */
  @Before
  def initialize():Unit = {
  }

  /**
    * Junit tests for the EnumMoney data type
    */
  @Test
  def makeEnumMoneyTest():Unit = {
    // simple test
    val dataType:String = "EnumMoney"
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = MoneyTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType,template.dataFormats(0), template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType,template.dataFormats(0), template.dataQualifiers(0)))
    }
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.5","1","2","3"))
    results = MoneyTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType,template.dataFormats(0), template.dataQualifiers(0))
    println("50% nullable")
    for(i <- 0 until 20) {
      println(MoneyTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType,template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0","1","2","3"))
    for(i <- 0 until 20) {
      println(MoneyTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType,template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0","1","2","3"))
    for(i <- 0 until 20) {
      println(MoneyTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType,template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
  }

  /**
    * Junit tests for the RandomMoney data type
    */
  @Test
  def makeRandomMoneyTest():Unit = {
    // test with length and min and max
    template.dataFormats = Array("min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","100")
    var results:String = MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test significant digits
    template.dataFormats = Array("min,max")
    template.dataQualifiers(0) = ArrayBuffer("10","1000000")
    results = MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test negatives
    template.dataFormats = Array("min,max")
    template.dataQualifiers(0) = ArrayBuffer("-10000","0")
    results = MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test rounding
    template.dataFormats = Array("min,max,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("10","1000000")
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
    template.dataFormats = Array("min,max,rounddown")
    template.dataQualifiers(0) = ArrayBuffer("-10","10")
    results = MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0))
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    template.dataFormats = Array("nullable,length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("0.5","10","1","100")
    println("50% nullable")
    for(i <- 0 until 20) {
      println(MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0","10","1","100"))
    for(i <- 0 until 20) {
      println(MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0","10","1","100"))
    for(i <- 0 until 20) {
      println(MoneyTypeGenerator.makeRandomMoney(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
  }

  /**
    * Junit tests for the ExternalMoney data type
    */
  @Test
  def makeExternalMoneyTest():Unit = {
    // simple test
    val dataType:String = "ExternalMoney"
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = MoneyTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType,template.dataFormats(0), template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(MoneyTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType,template.dataFormats(0), template.dataQualifiers(0)))
    }
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.5","1","2","3"))
    results = WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType,template.dataFormats(0), template.dataQualifiers(0))
    println("50% nullable")
    for(i <- 0 until 20) {
      println(WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType,template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0","1","2","3"))
    for(i <- 0 until 20) {
      println(WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType,template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0","1","2","3"))
    for(i <- 0 until 20) {
      println(WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType,template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
  }
}
