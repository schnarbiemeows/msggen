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
  * this object generates each of the different LocalDate types
  */
@Test
class DateTypeGeneratorTest {

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
    * Junit tests for the EnumDate data type
    */
  @Test
  def makeEnumDateTest():Unit = {
    // simple test
    val dataType:String = "EnumDate"
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("date1","date2","date3"))
    var results:String = DateTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0), template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(DateTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0), template.dataQualifiers(0)))
    }
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.5","string1","string2","string3"))
    println("50% nullable")
    for(i <- 0 until 20) {
      println(DateTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0","string1","string2","string3"))
    for(i <- 0 until 20) {
      println(DateTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0","string1","string2","string3"))
    for(i <- 0 until 20) {
      println(DateTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
  }

  /**
    * Junit tests for the RandomDateTime data type
    */
  @Test
  def makeRandomDateTest():Unit = {
    // simple normal test
    template.dataFormats = Array("format,end,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2019-10-10","1900-10-10")
    var results:String = DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 2) {
      println(DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with specifying no start date
    template.dataFormats = Array("format,end")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2019-10-10")
    results = DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying no end date
    template.dataFormats = Array("format,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd","2000-10-10")
    results = DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying no start date or end date
    template.dataFormats = Array("format")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd")
    results = DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying now, with a format, but no start date or end date
    template.dataFormats = Array("now,format")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd")
    results = DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying now, with nothing else
    template.dataFormats = Array("now")
    template.dataQualifiers(0) = ArrayBuffer()
    results = DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    template.dataFormats = Array("nullable,format,end,start")
    template.dataQualifiers = Array(ArrayBuffer("0.5","yyyy-MM-dd","2019-10-10","1900-10-10"))
    results = DateTypeGenerator.makeRandomDate(template.dataFormats(0), template.dataQualifiers(0))
    println("50% nullable")
    for(i <- 0 until 20) {
      println(DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0","yyyy-MM-dd","2019-10-10","1900-10-10"))
    for(i <- 0 until 20) {
      println(DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0","yyyy-MM-dd","2019-10-10","1900-10-10"))
    for(i <- 0 until 20) {
      println(DateTypeGenerator.makeRandomDate(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
  }

  /**
    * Junit tests for the ExternalDate data type
    */
  @Test
  def makeExternalDateTest():Unit = {
    // simple test
    val dataType:String = "EnumString"
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("date1","date2","date3"))
    var results:String = DateTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0), template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(DateTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0), template.dataQualifiers(0)))
    }
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.5","string1","string2","string3"))
    println("50% nullable")
    for(i <- 0 until 20) {
      println(DateTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0","string1","string2","string3"))
    for(i <- 0 until 20) {
      println(DateTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0","string1","string2","string3"))
    for(i <- 0 until 20) {
      println(DateTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
  }
}
