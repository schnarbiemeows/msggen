/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

import java.time._
import java.time.format.{DateTimeFormatter, DateTimeParseException}
import java.time.temporal.ChronoUnit




/**
  * utility class for manipulating DOB dates
  */
object DateUtils {

  val programStartTime:LocalTime = nowTime()
  /**
    * method to convert a LocalDate to a String
    * @param d - the LocalDate to convert
    * @return - the string equivalent
    */
  def getStringFromDate(d: LocalDate): String = {
    d.toString
  }

  /**
    * method to convert a LocalTime to a String
    * @param t - the LocalTime to convert
    * @return - the string equivalent
    */
  def getStringFromTime(t: LocalTime):String = {
    t.toString
  }

  /**
    * method to convert a LocalDateTime to a String
    * @param t - the LocalDateTime to convert
    * @return - the string equivalent
    */
  def getStringFromDateTime(t: LocalDateTime):String = {
    t.toString
  }

  /**
    * method to convert a String to a LocalDate
    * @param s - the string to convert
    * @param df - the DateTimeFormatter specified
    * @return - the LocalDate equivalent
    */
  def getDateFromString(s: String, df:String): LocalDate = {
    var dateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(df)
    var initialDate:LocalDate = LocalDate.parse(s,dateFormatter)
    initialDate
  }

  /**
    * method to convert a String to a LocalDateTime
    * @param s - the string to convert
    * @param df - the DateTimeFormatter specified
    * @return - the LocalDateTime equivalent
    */
  def getDateTimeFromString(s: String, df:String): LocalDateTime = {
    var dateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(df)
    var initialDate:LocalDateTime = LocalDateTime.parse(s,dateFormatter)
    initialDate
  }

  /**
    * method to add/subtract a number of days to a LocalDate
    * @param input - the primary's DOB
    * @param period - the difference in days of age between the primary and the spouse or the child
    * @return - the DOB of the primary or child
    */
  def addPeriodToLocalDate(input: LocalDate, period: Long): LocalDate = {
    var newLocalDate:LocalDate = input.plus(Period.ofDays(period.toInt))
    newLocalDate
  }

  /**
    * method to add/subtract a number of days to a LocalDateTime
    * @param input - the primary's DOB
    * @param period - the difference in days of age between the primary and the spouse or the child
    * @return - the DOB of the primary or child
    */
  def addPeriodToLocalDateTime(input: LocalDateTime, period: Long): LocalDateTime = {
    var newLocalDate:LocalDateTime = input.plus(Period.ofDays(period.toInt))
    newLocalDate
  }



  /**
    * method to figure out how old a person is(in days) based upon their birthdate
    *
    * @param input - the DOB of the primary
    * @return - the age of the primary in days
    */
  def getAgeInDays(input: LocalDateTime): Long = {
    val currentDate:LocalDateTime = nowDateTime()
    var daysOfAge:Long = ChronoUnit.DAYS.between(input,currentDate)
    daysOfAge
  }

  /**
    * method to figure out how old a person is(in days) based upon their birthdate
    * @param input - the DOB of the primary
    * @return - the age of the primary in days
    */
  def getAgeInDays(input: LocalDate): Long = {
    val currentDate:LocalDate = now()
    var daysOfAge:Long = Period.between(input,currentDate).getDays
    daysOfAge
  }

  /**
    * method to get the current date as a LocalDate
    * @return - the current date
    */
  def now(): LocalDate = {
    LocalDate.now()
  }

  /**
    * method to get the current time as a LocalTime
    * @return - the current date
    */
  def nowTime(): LocalTime = {
    LocalTime.now()
  }

  /**
    * method to get the current date as a LocalDateTime
    * @return - the current date
    */
  def nowDateTime(): LocalDateTime = {
    LocalDateTime.now()
  }

  /**
    * method to get the difference between two times in microseconds
    * @param input = start time
    * @return
    */
  def getDifferenceInMicroseconds(input: LocalTime): Tuple2[Long, LocalTime] = {
    val current = nowTime()
    val output:Long = ChronoUnit.MICROS.between(input,current)
    (output,nowTime())
  }

  /**
    * method to get the difference between two times in milliseconds
    * @param input = start time
    * @return
    */
  def getDifferenceInMilliseconds(input: LocalTime): Tuple2[Long, LocalTime] = {
    val current = nowTime()
    val output:Long = ChronoUnit.MILLIS.between(input,current)
    (output,nowTime())
  }

  /**
    * method to get the difference between two times in seconds
    * @param input
    * @return
    */
  def getDifferenceInSeconds(input: LocalTime): Tuple2[Long, LocalTime] = {
    val current = nowTime()
    val output:Long = Duration.between(input,current).getSeconds
    (output,nowTime())
  }

  /**
    * method to get the total program run time in seconds
    * @return
    */
  def getFinalProgramRunTime(): Long = {
    val current = nowTime()
    ChronoUnit.MILLIS.between(programStartTime,current)
  }

  /**
    * method that will test if the LocalDate format specified is valid
    * @param format - format that the user specified
    * @return - true or false
    */
  def isDateFormatValid(format:String):Boolean = {
    var isFormatValid:Boolean = true
    try{
      var thisformat:DateTimeFormatter = DateTimeFormatter.ofPattern(format)
      var date:String = thisformat.format(LocalDate.now())
    } catch  {
      case e: Exception => { isFormatValid = false }
    }
    isFormatValid
  }

  /**
    * method that will test if the LocalDateTime format specified is valid
    * @param format - format that the user specified
    * @return - true or false
    */
  def isDateTimeFormatValid(format:String):Boolean = {
    var isFormatValid:Boolean = true
    try{
      var thisformat:DateTimeFormatter = DateTimeFormatter.ofPattern(format)
      var date:String = thisformat.format(LocalDateTime.now())
    } catch  {
      case e: Exception => { isFormatValid = false }
    }
    isFormatValid
  }

  /**
    * method that test to see if the string specified will
    * convert to a LocalDate
    * @param s - string to convert
    * @param df - DateFormatter specified(already validated)
    * @return - true or false
    */
  def willStringParseToLocalDate(s: String, df:String): Boolean = {
    var isFormatValid:Boolean = true

    isFormatValid
  }

  /**
    * method that test to see if the string specified will
    * convert to a LocalDateTime
    * @param s - string to convert
    * @param df - DateFormatter specified(already validated)
    * @return - true or false
    */
  def willStringParseToLocalDateTime(s: String, df:String): Boolean = {
    var isFormatValid:Boolean = true
    var dateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(df)
    try {
      var initialDate:LocalDateTime = LocalDateTime.parse(s,dateFormatter)
    } catch {
      case e: DateTimeParseException => { isFormatValid = false }
    }
    isFormatValid
  }
}
