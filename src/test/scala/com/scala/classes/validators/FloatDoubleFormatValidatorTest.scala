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
  * class for unit testing the FloatDoubleFormatValidator class methods
  */
@Test
class FloatDoubleFormatValidatorTest {

  @Test
  def validateEnumFloatFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = FloatDoubleFormatValidator.validateEnumFloatFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRandomFloatFormat():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = FloatDoubleFormatValidator.validateRandomFloatFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateExternalFloatFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = FloatDoubleFormatValidator.validateExternalFloatFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRangeFloatFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = FloatDoubleFormatValidator.validateRangeFloatFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateEnumDoubleFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = FloatDoubleFormatValidator.validateEnumDoubleFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRandomDoubleFormat():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = FloatDoubleFormatValidator.validateRandomDoubleFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateExternalDoubleFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = FloatDoubleFormatValidator.validateExternalDoubleFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRangeDoubleFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = FloatDoubleFormatValidator.validateRangeDoubleFormat(input)
    assertTrue(results._1)
  }
}
