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
  * class for unit testing the IntLongFormatValidator class methods
  */
@Test
class IntLongFormatValidatorTest {

  @Test
  def validateEnumIntFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = IntLongFormatValidator.validateEnumIntFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRandomIntFormat():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = IntLongFormatValidator.validateRandomIntFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateExternalIntFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = IntLongFormatValidator.validateExternalIntFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRangeIntFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = IntLongFormatValidator.validateRangeIntFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateEnumLongFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = IntLongFormatValidator.validateEnumLongFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRandomLongFormat():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = IntLongFormatValidator.validateRandomLongFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateExternalLongFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = IntLongFormatValidator.validateExternalLongFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRangeLongFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = IntLongFormatValidator.validateRangeLongFormat(input)
    assertTrue(results._1)
  }
}
