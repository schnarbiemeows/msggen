/*
 * Created 2019 by Dylan Kessler 
 */

package com.scala.classes.validators

import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.utilities.PropertyLoader
import org.junit.{Before, Test}
import org.junit.Assert._
import java.util.Properties

/**
  * class for unit testing the DateTimeFormatValidator class methods
  */
@Test
class DateTimeFormatValidatorTest {

  @Test
  def validateEnumDateFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = DateTimeFormatValidator.validateEnumDateFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRandomDateFormat():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = DateTimeFormatValidator.validateRandomDateFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateExternalDateFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = DateTimeFormatValidator.validateExternalDateFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRangeDateFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = DateTimeFormatValidator.validateRangeDateFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateEnumTimeFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = DateTimeFormatValidator.validateEnumTimeFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRandomTimeFormat():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = DateTimeFormatValidator.validateRandomTimeFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateExternalTimeFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = DateTimeFormatValidator.validateExternalTimeFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRangeTimeFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = DateTimeFormatValidator.validateRangeTimeFormat(input)
    assertTrue(results._1)
  }
}
