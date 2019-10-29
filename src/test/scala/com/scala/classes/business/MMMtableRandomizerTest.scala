/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.utilities.{Configuration, PropertyLoader}
import com.scala.classes.validators.ExcelSheetValidatorTestMocks
import org.junit.{Before, Test}

/**
  * this object tests the MMMtableRandomizer object
  */
@Test
class MMMtableRandomizerTest {

  var template:GenericRecordsTemplate = new ExcelSheetValidatorTestMocks().getBlankTemplate()

  /**
    * initialization
    */
  @Before
  def initialize():Unit = {
  }

  @Test
  def test():Unit = {
    val result = MMMtableRandomizer.makeBinaryDecision(PropertyLoader.getProperty(Configuration.MODE3_LINE2_ADDR_PERCENT).toDouble)
    assert(result != None)
    println(result)
  }
}
