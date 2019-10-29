/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

import org.junit.Assert._
import org.junit.{Before, Test}

/**
  * class to test the NumUtility class
  */
@Test
class NumUtilitiesTest {

  /**
    * initialization
    */
  @Before
  def initialize():Unit = {
    NumUtility.initialize()
  }

  /**
    * method to convert a Long or Int to a String and pad it with leading zeroes, if needed
    * this is used to pad subscriber and account IDs
    * if the length specified is negative, it won't fail because subId will never be null
    * so it will always be a length > 0
    * if the length is smaller than the actual length of the number to convert, just
    * return the number
    * param subId = subscriber ID to convert
    * param length = length to pad to
    * return subId.toString
    */
  @Test
  def padIntToStringTest():Unit = {
    var subId:Long = 0L
    var length:Int = -1
    var results = NumUtility.padIntToString(subId,length)
    assertNotNull(results)
    assertEquals("0",results)
    length = 0
    results = NumUtility.padIntToString(subId,length)
    assertEquals("0",results)
    length = 1
    results = NumUtility.padIntToString(subId,length)
    assertEquals("0",results)
    length = 2
    results = NumUtility.padIntToString(subId,length)
    assertEquals("00",results)
  }

  /**
    * method for truncating a Double to an Int
    * if the result is greater than Int.MaxValue, it Int.MaxValue is returned
    * param multiplicationFactor = multiplication factor
    * param percentage = percentage to convert
    * return number
    */
  @Test
  def convertPercentageToIntTest():Unit = {
    var multiplicationFactor:Int = 0
    var percentage:Double = 0f
    var results = NumUtility.convertPercentageToInt(multiplicationFactor,percentage)
    assertNotNull(results)
    assertEquals(0L,results)
    multiplicationFactor = -1
    results = NumUtility.convertPercentageToInt(multiplicationFactor,percentage)
    assertNotNull(results)
    assertEquals(0L,results)
    percentage = .10
    results = NumUtility.convertPercentageToInt(multiplicationFactor,percentage)
    assertNotNull(results)
    println(results)
    assertEquals(0L,results)
    multiplicationFactor = 100
    percentage= .50
    results = NumUtility.convertPercentageToInt(multiplicationFactor,percentage)
    assertNotNull(results)
    println(results)
    assertEquals(50,results)
    // greater than 100%
    multiplicationFactor = 100
    percentage= 1.50
    results = NumUtility.convertPercentageToInt(multiplicationFactor,percentage)
    assertNotNull(results)
    println(results)
    assertEquals(150,results)
    // negative multiplicationFactor
    multiplicationFactor = -100
    percentage= 1.50
    results = NumUtility.convertPercentageToInt(multiplicationFactor,percentage)
    assertNotNull(results)
    println(results)
    assertEquals(-150,results)
    // negative percentage
    multiplicationFactor = 100
    percentage= -.50
    results = NumUtility.convertPercentageToInt(multiplicationFactor,percentage)
    assertNotNull(results)
    println(results)
    assertEquals(-50,results)
    // both negative
    multiplicationFactor = -100
    percentage= -.50
    results = NumUtility.convertPercentageToInt(multiplicationFactor,percentage)
    assertNotNull(results)
    println(results)
    assertEquals(50,results)
    // numbers out of range, Int.MaxValue is returned
    multiplicationFactor = Int.MaxValue
    percentage= 2.01
    results = NumUtility.convertPercentageToInt(multiplicationFactor,percentage)
    assertNotNull(results)
    println(results)
    assert(results==Int.MaxValue)
  }
}
