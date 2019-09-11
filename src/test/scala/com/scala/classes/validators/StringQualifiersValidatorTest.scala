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
  * class for unit testing the StringQualifiersValidator class methods
  */
@Test
class StringQualifiersValidatorTest {

  var properties:Properties = _
  var template:GenericRecordsTemplate = _

  @Before
  def initialize():Unit = {
    properties = PropertyLoader.getProperties("config.properties")
    var mocks:ExcelSheetValidatorTestMocks = new ExcelSheetValidatorTestMocks(properties)
    template = mocks.getBlankTemplate()

  }

  @Test
  def validateEnumStringQualifiersTest():Unit = {
    template.dataTypes = Array("EnumString")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(Array("something"))
    val results:Tuple2[Boolean,String] = StringQualifiersValidator.validateEnumStringQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }

  @Test
  def validateRandomStringQualifiersTest():Unit = {
    template.dataTypes = Array("RandomString")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = StringQualifiersValidator.validateRandomStringQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)

  }

  @Test
  def validateExternalStringQualifiersTest():Unit = {
    template.dataTypes = Array("ExternalString")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    template.dataQualifiers = Array(Array("C:\\home\\schnarbies\\output\\ssns.txt"))
    val results:Tuple2[Boolean,String] = StringQualifiersValidator.validateExternalStringQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    println(results._2)
    assertTrue(results._1)
  }

  @Test
  def validateRangeStringQualifiersTest():Unit = {
    template.dataTypes = Array("RangedString")
    template.fields = Array("field1")
    template.dataFormats = Array("NONE")
    val results:Tuple2[Boolean,String] = StringQualifiersValidator.validateRangeStringQualifiers(template.dataTypes(0),template.dataFormats(0),template.dataQualifiers(0))
    assertTrue(results._1)
  }
}
