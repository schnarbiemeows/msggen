/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.utilities.{Configuration, PropertyLoader}
import com.scala.classes.validators.ExcelSheetValidatorTestMocks
import org.junit.Assert._
import org.junit.{Before, Test}

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different Long types
  */
@Test
class WholeNumberTypeGeneratorTest {

  var template:GenericRecordsTemplate = new ExcelSheetValidatorTestMocks().getBlankTemplate()

  /**
    * initialization
    */
  @Before
  def initialize():Unit = {
  }

  /**
    * Junit tests for the EnumLong data type
    */
  @Test
  def makeEnumWholeNumberTest():Unit = {
    // simple test
    val dataType:String = "EnumLong"
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.5","1","2","3"))
    results = WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0), template.dataQualifiers(0))
    println("50% nullable")
    for(i <- 0 until 20) {
      println(WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0","1","2","3"))
    for(i <- 0 until 20) {
      println(WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0","1","2","3"))
    for(i <- 0 until 20) {
      println(WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
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
    template.dataFormats = Array("nullable,length,min,max")
    template.dataQualifiers(0) = ArrayBuffer("0.5","10","1","100")
    println("50% nullable")
    for(i <- 0 until 20) {
      println(WholeNumberTypeGenerator.makeRandomWholeNumber(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0","10","1","100"))
    for(i <- 0 until 20) {
      println(WholeNumberTypeGenerator.makeRandomWholeNumber(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0","10","1","100"))
    for(i <- 0 until 20) {
      println(WholeNumberTypeGenerator.makeRandomWholeNumber(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
  }

  @Test
  def makeRandgedWholeNumberTest():Unit = {
    // simple test
    val dataType:String = "RangedInt"
    template.dataFormats = Array("linbase,linadd")
    template.dataQualifiers = Array(ArrayBuffer("0","1"))
    var arrayNum:Int = 0
    var fileNum:Int = 0
    var results:String = WholeNumberTypeGenerator.makeRangedWholeNumber(dataType, template.dataFormats(0), template.dataQualifiers(0),arrayNum,fileNum)
    assertEquals(results.toLong,0)
    println(results)
    arrayNum = 12
    fileNum = 3
    PropertyLoader.setProperty(Configuration.MODE4_NUM_RECORDS,"50")
    results = WholeNumberTypeGenerator.makeRangedWholeNumber(dataType, template.dataFormats(0), template.dataQualifiers(0),arrayNum,fileNum)
    assertEquals(results.toLong,162)
    println(results)
    arrayNum = 0
    fileNum = 0
    for(i<- 0 until 25) {
      println(WholeNumberTypeGenerator.makeRangedWholeNumber(dataType, template.dataFormats(0), template.dataQualifiers(0),arrayNum,fileNum))
      arrayNum+=1
    }
    arrayNum = 0
    fileNum = 3
    for(i<- 0 until 25) {
      println(WholeNumberTypeGenerator.makeRangedWholeNumber(dataType, template.dataFormats(0), template.dataQualifiers(0),arrayNum,fileNum))
      arrayNum+=1
    }
    template.dataQualifiers = Array(ArrayBuffer("0","-1"))
    arrayNum = 0
    fileNum = 0
    for(i<- 0 until 25) {
      println(WholeNumberTypeGenerator.makeRangedWholeNumber(dataType, template.dataFormats(0), template.dataQualifiers(0),arrayNum,fileNum))
      arrayNum+=1
    }
    template.dataQualifiers = Array(ArrayBuffer("100","-1"))
    arrayNum = 0
    fileNum = 0
    for(i<- 0 until 25) {
      println(WholeNumberTypeGenerator.makeRangedWholeNumber(dataType, template.dataFormats(0), template.dataQualifiers(0),arrayNum,fileNum))
      arrayNum+=1
    }
  }
  /**
    * Junit tests for the ExternalLong data type
    */
  @Test
  def makeExternalWholeNumberTest():Unit = {
    // simple test
    val dataType:String = "ExternalLong"
    template.dataFormats = Array("None")
    template.dataQualifiers = Array(ArrayBuffer("1","2","3"))
    var results:String = WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0), template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.5","1","2","3"))
    results = WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0), template.dataQualifiers(0))
    println("50% nullable")
    for(i <- 0 until 20) {
      println(WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0","1","2","3"))
    for(i <- 0 until 20) {
      println(WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0","1","2","3"))
    for(i <- 0 until 20) {
      println(WholeNumberTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
  }
}
