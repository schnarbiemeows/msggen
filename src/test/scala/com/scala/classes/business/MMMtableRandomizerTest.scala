/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import java.util.Properties

import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.utilities.{Configuration, PropertyLoader}
import org.junit.{Before, Test}

/**
  * this object tests the MMMtableRandomizer object
  */
@Test
class MMMtableRandomizerTest {

  var properties:Properties = _
  var template:GenericRecordsTemplate = _

  /**
    * initialization
    */
  @Before
  def initialize():Unit = {
    properties = PropertyLoader.getProperties("C:\\home\\schnarbies\\config\\config.properties")
  }

  @Test
  def test():Unit = {
    val result = MMMtableRandomizer.makeBinaryDecision(properties.getProperty(Configuration.MODE3_LINE2_ADDR_PERCENT).toDouble)
    assert(result != None)
    println(result)
  }
}
