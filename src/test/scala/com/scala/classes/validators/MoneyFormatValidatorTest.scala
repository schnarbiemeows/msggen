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
  * class for unit testing the MoneyFormatValidator class methods
  */
@Test
class MoneyFormatValidatorTest {

  @Test
  def validateEnumMoneyFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = MoneyFormatValidator.validateEnumMoneyFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRandomMoneyFormat():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = MoneyFormatValidator.validateRandomMoneyFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateExternalMoneyFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = MoneyFormatValidator.validateExternalMoneyFormat(input)
    assertTrue(results._1)
  }

  @Test
  def validateRangeMoneyFormatTest():Unit = {
    val input = "NONE"
    val results:Tuple2[Boolean,String] = MoneyFormatValidator.validateRangeMoneyFormat(input)
    assertTrue(results._1)
  }
}
