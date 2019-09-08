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
  * class for unit testing the StringFormatValidator class methods
  */
@Test
class StringFormatValidatorTest {


  @Test
  def validateEnumStringFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = StringFormatValidator.validateEnumStringFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRandomStringFormat():Unit = {
    var input = "NONE"
    var results:Tuple2[Boolean,String] = StringFormatValidator.validateRandomStringFormat(input)
    assertTrue(results._1)
    //input = "length=10"
    //results = StringFormatValidator.validateRandomStringFormat(input)
    //assertTrue(results._1)
    input = "length="
    results = StringFormatValidator.validateRandomStringFormat(input)
    assertFalse(results._1)
    println(results._2)
    input = "length=-1"
    results = StringFormatValidator.validateRandomStringFormat(input)
    assertFalse(results._1)
    println(results._2)
    input = "xxxxxx"
    results = StringFormatValidator.validateRandomStringFormat(input)
    assertFalse(results._1)
    println(results._2)
    input = "x=x=x"
    results = StringFormatValidator.validateRandomStringFormat(input)
    assertFalse(results._1)
    println(results._2)
    input = "=x"
    results = StringFormatValidator.validateRandomStringFormat(input)
    assertFalse(results._1)
    println(results._2)
    input = "lower~chars=abc"
    results = StringFormatValidator.validateRandomStringFormat(input)
    assertTrue(results._1)
    println(results._2)

  }

  @Test
  def validateExternalStringFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = StringFormatValidator.validateExternalStringFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRangeStringFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = StringFormatValidator.validateRangeStringFormat(input)
    assertTrue(results._1)
  }
}
