/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import java.util.Properties

import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.utilities.{Configuration, FileIO, PropertyLoader}
import com.scala.classes.validators.ExcelSheetValidatorTestMocks
import org.junit.Assert._
import org.junit.{Before, Test}

/**
  * this object tests the HiveTableCreator object
  */
@Test
class HiveTableCreatorTest {

  var properties:Properties = _
  var template:GenericRecordsTemplate = _

  /**
    * initialization
    */
  @Before
  def initialize():Unit = {
    properties = PropertyLoader.getProperties("C:\\home\\schnarbies\\config\\config.properties")
    var mocks:ExcelSheetValidatorTestMocks = new ExcelSheetValidatorTestMocks(properties)
    template = mocks.getBlankTemplate()
  }

  @Test
  def testTableCreation():Unit = {
    template.fields=Array("string1","int2","long3","float4","double5","money6","date7","dateTime8")
    template.dataTypes=Array("RandomString","RandomInt","RandomLong","RandomFloat","RandomDouble","RandomMoney",
    "RandomDate","RandomDateTime")
    // test external csv table
    properties.setProperty(Configuration.MODE5_HIVE_EXTERNAL_TABLE,"true")
    properties.setProperty(Configuration.MODE4_OUTPUT_FILE_TYPE,"CSV")
    var results = HiveTableCreator.makeTableArray(template,properties)
    assert(results.size==14)
    for(i <- 0 until results.size ) {
      println(results(i))
    }
    // test manaed csv table
    properties.setProperty(Configuration.MODE5_HIVE_EXTERNAL_TABLE,"false")
    results = HiveTableCreator.makeTableArray(template,properties)
    assert(results.size==14)
    for(i <- 0 until results.size ) {
      println(results(i))
    }
    // test managed json table
    properties.setProperty(Configuration.MODE4_OUTPUT_FILE_TYPE,"JSON")
    results = HiveTableCreator.makeTableArray(template,properties)
    assert(results.size==12)
    for(i <- 0 until results.size ) {
      println(results(i))
    }
    // test external json table
    properties.setProperty(Configuration.MODE5_HIVE_EXTERNAL_TABLE,"true")
    results = HiveTableCreator.makeTableArray(template,properties)
    assert(results.size==12)
    for(i <- 0 until results.size ) {
      println(results(i))
    }
    val filepath = properties.getProperty(Configuration.MODE5_HIVE_OUTPUTFILE)
    val diditwork = FileIO.outputAnyListToFile(results.toList,filepath)
    assertTrue(diditwork)
  }
}
