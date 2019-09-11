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
class MoneyQualifiersValidatorTest {

  var properties:Properties = _
  var template:GenericRecordsTemplate = _

  /**
    *
    */
  @Before
  def initialize():Unit = {
    properties = PropertyLoader.getProperties("config.properties")
    var mocks:ExcelSheetValidatorTestMocks = new ExcelSheetValidatorTestMocks(properties)
    template = mocks.getBlankTemplate()

  }

  /**
    *
    */
  @Test
  def validateEnumMoneyQualifiersTest():Unit = {
    template.dataTypes = Array("EnumMoney")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(Array("something"))
    val results:Tuple2[Boolean,String] = MoneyQualifiersValidator.validateEnumMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateRandomMoneyQualifiersTest():Unit = {
    template.dataTypes = Array("RandomMoney")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = MoneyQualifiersValidator.validateRandomMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateExternalMoneyQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalMoney")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(Array("C:\\home\\schnarbies\\output\\ssns.txt"))
    val results:Tuple2[Boolean,String] = MoneyQualifiersValidator.validateExternalMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  /**
    *
    */
  @Test
  def validateRangeMoneyQualifiersTest():Unit = {
    template.dataTypes = Array("RangedMoney")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = MoneyQualifiersValidator.validateRangeMoneyQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }
}
