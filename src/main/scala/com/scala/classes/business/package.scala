/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes

import java.util.concurrent.ThreadLocalRandom

import com.scala.classes.utilities.Configuration

import scala.math.BigDecimal
import scala.util.Random

package object business {

  /**
    * method to generate a random integer
    * @param min = minimum possible value
    * @param max = maximum possible value
    * @return result
    */
  def randomInteger(min: Int, max: Int): Int = {
    val rand = new Random
    val result = rand.nextInt((max-1 - min) + 1) + min
    result
  }

  /**
    * method to generate a random integer
    * @param min = minimum possible value
    * @param max = maximum possible value
    * @return result
    */
  def randomLong(min: Long, max: Long): Long = {
    val rand: ThreadLocalRandom = ThreadLocalRandom.current()
    val result = rand.nextLong(min,max)
    result
  }
  /**
    * method to generate a random alphanumeric String of a given length
    * @param size = length of the String
    * @return builder.toString
    */
  def randomAlphaNumeric(size: Int): String = {
    val builder = new StringBuilder
    var count = size
    while(count>0) {
      val character = (Math.random * Configuration.ALPHA_NUMERIC_STRING.length).asInstanceOf[Int]
      builder.append(Configuration.ALPHA_NUMERIC_STRING.charAt(character))
      count-=1
    }
    builder.toString
  }

  /**
    * method to generate a random money value
    * @param min = lowest possile value
    * @param max = highest possible value
    * @return Double
    */
  def randomMoney(min: Double ,max: Double):Double = {
    val rand = new Random
    var generatedDouble:Double = rand.nextDouble()*(max-min)+min
    var roundedDouble = BigDecimal(generatedDouble).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    roundedDouble
  }

  /**
    * method to generate a random Double value
    * @param min = lowest possile value
    * @param max = highest possible value
    * @param precision = precision after the decimal point
    * @return Double
    */
  def randomDouble(min: Double ,max: Double, precision:Int):Double = {
    val rand = new Random
    var generatedDouble:Double = rand.nextDouble()*(max-min)+min
    var roundedDouble = BigDecimal(generatedDouble).setScale(precision, BigDecimal.RoundingMode.HALF_UP).toDouble
    roundedDouble
  }

  /**
    * method to generate a random Float value
    * @param min = lowest possile value
    * @param max = highest possible value
    * @param precision = precision after the decimal point
    * @return Float
    */
  def randomFloat(min: Float ,max: Float, precision:Int):Double = {
    val rand = new Random
    var generatedFloat:Float = rand.nextFloat()*(max-min)+min
    var roundedFloat:Float = BigDecimal(generatedFloat).setScale(2, BigDecimal.RoundingMode.HALF_UP).toFloat
    roundedFloat
  }

}
