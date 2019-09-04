/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

import java.text.SimpleDateFormat
import java.util.Calendar
import java.time.{Duration, LocalDate, LocalTime, Period}
import java.time.LocalDate.{now, _}
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
    * method to convert a String to a LocalDate
    * @param s - the string to convert
    * @return - the LocalDate equivalent
    */
  def getDateFromString(s: String): LocalDate = {
    var initialDate:LocalDate = parse(s)
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
}
