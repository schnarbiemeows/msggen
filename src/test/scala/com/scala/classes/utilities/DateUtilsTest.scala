/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

import java.time.{LocalDate, LocalTime}

import org.junit.Assert._
import org.junit.{Before, Test}

/**
  * Test class that tests the DateUtils class
  */
@Test
class DateUtilsTest {

  /**
    * initialization
    */
  @Before
  def initialize(): Unit = {
  }

  // TODO - need to add LocalDateTime test methods to this

  /**
    *
    */
  @Test
  def getStringFromDateTest(): Unit = {
    val localDate1 = LocalDate.now()
    val result = DateUtils.getStringFromDate(localDate1)
    assertNotNull(result)
  }

  /**
    *
    */
  @Test(expected = classOf[NullPointerException])
  def getDateFromStringTest():Unit = {
    val result = null
    val format:String = "yyyy-MM-dd"
    val localDate2:LocalDate = DateUtils.getDateFromString(result,format)
    assertNotNull(localDate2)
  }

  /**
    *
    */
  @Test
  def getDateFromStringTest2():Unit = {
    val format:String = "yyyy-MM-dd"
    val localDate1 = LocalDate.now()
    val result = DateUtils.getStringFromDate(localDate1)
    val localDate2:LocalDate = DateUtils.getDateFromString(result,format)
    assertNotNull(localDate2)
    assertEquals(localDate2,localDate1)
  }

  /**
    *
    */
  @Test
  def addPeriodToLocalDateTest():Unit = {
    val localDate1 = LocalDate.now()
    val long1 = 1L
    val long2 = -1L
    val result1:LocalDate = DateUtils.addPeriodToLocalDate(localDate1,long1)
    val result2:LocalDate = DateUtils.addPeriodToLocalDate(localDate1,long2)
    assertNotNull(result1)
    assertNotNull(result2)
    assert(result1.isAfter(result2))
  }

  /**
    *
    */
  @Test
  def getAgeInDaysTest():Unit = {
    val localDate2 = LocalDate.of(2018,1,1)
    val ageInDays:Long = DateUtils.getAgeInDays(localDate2)
    val newborn:Long = DateUtils.getAgeInDays(LocalDate.now())
    assertNotNull(ageInDays)
    assert(ageInDays>0)
    assertEquals(newborn,0)
    println(s"age in days = ${ageInDays}")
  }

  /**
    *
    */
  @Test
  def nowTest():Unit = {
    assertNotNull(DateUtils.now())
  }

  /**
    *
     */
  @Test
  def nowTimeTest():Unit = {
    assertNotNull(DateUtils.nowTime())
  }

  /**
    *
    */
  @Test
  def getDifferenceInMicrosecondsTest():Unit = {
    val localTime1 = LocalTime.now()
    Thread.sleep(1)
    val result:Tuple2[Long, LocalTime] = DateUtils.getDifferenceInMicroseconds(localTime1)
    assert(result._1>0)
    assertNotNull(result._2)
  }

  /**
    *
    */
  @Test
  def getDifferenceInMillisecondsTest():Unit = {
    val localTime1 = LocalTime.now()
    Thread.sleep(1)
    val result:Tuple2[Long, LocalTime] = DateUtils.getDifferenceInMilliseconds(localTime1)
    assert(result._1>0)
    assertNotNull(result._2)
  }

  /**
    *
    */
  @Test
  def getDifferenceInSecondsTest():Unit = {
    val localTime1 = LocalTime.now()
    Thread.sleep(1000)
    val result:Tuple2[Long, LocalTime] = DateUtils.getDifferenceInSeconds(localTime1)
    assert(result._1>0)
    assertNotNull(result._2)
  }

  /**
    * methods to test if the isDateFormatValid works
    */
  @Test
  def isDateFormatValidTest():Unit = {
    var format:String = "yyyy-MM-dd"
    var isValid:Boolean = DateUtils.isDateFormatValid(format)
    assertTrue(isValid)
    format = "yyyy-MM-dd HH:mm:ss"
    isValid = DateUtils.isDateFormatValid(format)
    assertFalse(isValid)
  }

  /**
    * methods to test if the isDateTimeFormatValid works
    */
  @Test
  def isDateTimeFormatValidTest():Unit = {
    var format:String = "yyyy-MM-dd HH:mm:ss"
    var isValid:Boolean = DateUtils.isDateTimeFormatValid(format)
    assertTrue(isValid)
    format = "yyyy-MM-dd HH:mm:sss"
    isValid = DateUtils.isDateTimeFormatValid(format)
    assertFalse(isValid)
  }

  /**
    * methods to test if the isDateFormatValid works
    */
  @Test
  def willStringParseToLocalDateTest():Unit = {
    var stringToConvert:String = "2019-04-12"
    val format:String = "yyyy-MM-dd"
    var isValid:Boolean = DateUtils.willStringParseToLocalDate(stringToConvert,format)
    assertTrue(isValid)
    stringToConvert = "20xx-yy-mm"
    isValid = DateUtils.willStringParseToLocalDate(stringToConvert,format)
    assertFalse(isValid)
  }

  /**
    * methods to test if the isDateTimeFormatValid works
    */
  @Test
  def willStringParseToLocalDateTimeTest():Unit = {
    var stringToConvert:String = "2019-04-12 00:00:00"
    val format:String = "yyyy-MM-dd HH:mm:ss"
    var isValid:Boolean = DateUtils.willStringParseToLocalDateTime(stringToConvert,format)
    assertTrue(isValid)
    stringToConvert = "2019-04-12 00:00:000"
    isValid = DateUtils.willStringParseToLocalDateTime(stringToConvert,format)
    assertFalse(isValid)
  }
  /*
  can't really test this
    def getFinalProgramRunTime(): Long = {
    val current = nowTime()
    ChronoUnit.MILLIS.between(programStartTime,current)
  }*/
}
