/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.validators.ExcelSheetValidatorTestMocks
import org.junit.Assert._
import org.junit.{Before, Test}

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different LocalDateTime types
  */
@Test
class DateTimeTypeGeneratorTest {

  var template:GenericRecordsTemplate = new ExcelSheetValidatorTestMocks().getBlankTemplate()

  /**
    * initialization
    */
  @Before
  def initialize():Unit = {
  }

  /**
    * Junit tests for the EnumDateTime data type
    */
  @Test
  def makeEnumDateTimeTest():Unit = {
    // simple test
    val dataType:String = "EnumDateTime"
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("date1","date2","date3"))
    var results:String = DateTimeTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0), template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(DateTimeTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0), template.dataQualifiers(0)))
    }
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.5","string1","string2","string3"))
    println("50% nullable")
    for(i <- 0 until 20) {
      println(DateTimeTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0","string1","string2","string3"))
    for(i <- 0 until 20) {
      println(DateTimeTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0","string1","string2","string3"))
    for(i <- 0 until 20) {
      println(DateTimeTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
  }

  /**
    * Junit tests for the RandomDateTime data type
    */
  @Test
  def makeRandomDateTimeTest():Unit = {
    // simple normal test
    template.dataFormats = Array("format,end,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss","2019-10-10 00:00:00","2018-10-10 00:00:00")
    var results:String = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 1) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with short range to see if the edge cases are beng handled
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH","2018-10-15 12","1900-10-10 02")
    for(i <- 0 until 1) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with specifying no start date
    template.dataFormats = Array("format,end")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm","2019-10-10 00:00")
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 1) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with specifying no end date
    template.dataFormats = Array("format,start")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss","2000-01-01 00:00:00")
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 1) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    // test with specifying no start date or end date
    template.dataFormats = Array("format")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss")
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 1) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    template.dataFormats = Array("NONE")
    template.dataQualifiers(0) = ArrayBuffer()
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying now with a format, but no start date or end date
    template.dataFormats = Array("format,now")
    template.dataQualifiers(0) = ArrayBuffer("yyyy-MM-dd HH:mm:ss")
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    // test with specifying now with no format or start/end date
    template.dataFormats = Array("now")
    template.dataQualifiers(0) = ArrayBuffer()
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    template.dataFormats = Array("format,start,end")
    template.dataQualifiers(0) = ArrayBuffer("dd/MM/yyyy HH:mm","01/01/1900 00:00","31/12/2000 00:00")
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    template.dataFormats = Array("nullable,format,start,end")
    template.dataQualifiers = Array(ArrayBuffer("0.5","dd/MM/yyyy HH:mm","01/01/1900 00:00","31/12/2000 00:00"))
    results = DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0), template.dataQualifiers(0))
    println("50% nullable")
    for(i <- 0 until 20) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0","dd/MM/yyyy HH:mm","01/01/1900 00:00","31/12/2000 00:00"))
    for(i <- 0 until 20) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0","dd/MM/yyyy HH:mm","01/01/1900 00:00","31/12/2000 00:00"))
    for(i <- 0 until 20) {
      println(DateTimeTypeGenerator.makeRandomDateTime(template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
  }

  /**
    * Junit tests for the ExternalDateTime data type
    */
  @Test
  def makeExternalDateTimeTest():Unit = {
    // simple test
    val dataType:String = "ExternalDateTime"
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(ArrayBuffer("date1","date2","date3"))
    var results:String = DateTimeTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0), template.dataQualifiers(0))
    assertNotNull(results)
    println(results)
    for(i <- 0 until 10) {
      println(DateTimeTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0), template.dataQualifiers(0)))
    }
    template.dataFormats = Array("nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.5","string1","string2","string3"))
    println("50% nullable")
    for(i <- 0 until 20) {
      println(DateTimeTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("0% nullable")
    template.dataQualifiers = Array(ArrayBuffer("0.0","string1","string2","string3"))
    for(i <- 0 until 20) {
      println(DateTimeTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("100% nullable")
    template.dataQualifiers = Array(ArrayBuffer("1.0","string1","string2","string3"))
    for(i <- 0 until 20) {
      println(DateTimeTypeGenerator.makeRandonizedExternalOrEnumDataType(dataType, template.dataFormats(0),template.dataQualifiers(0)))
    }
    println("DONE")
  }
}
