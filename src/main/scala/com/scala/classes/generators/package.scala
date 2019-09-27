/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes

import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.{Duration, LocalDate, LocalDateTime}
import java.util.concurrent.ThreadLocalRandom

import com.scala.classes.posos.DataTypeFormats
import com.scala.classes.utilities.{Configuration, DateUtils}

import scala.math.BigDecimal
import scala.math.BigDecimal.RoundingMode.Value
import scala.util.Random

package object generators {

  /**
    * this method will filter out any keywords in the format string that do not need
    * the return array should index the same as the values array, so returnArray(0)
    * will correspond to dataValues(x)(0),for example
    * @param dataTypeName - data type
    * @param format - list of formats for this data type
    * @return - (isValidated,message)
    */
  def filterQualifiers(dataTypeName:String, format:String):Tuple2[Array[String],Array[String]] = {
    val keywords:Array[String] = format.split(Configuration.DELIMITTER1)
    val qualifiers:Set[String] = DataTypeFormats.keysThatNeedQualifiers(dataTypeName)
    val needsQualifiers:Array[String] = keywords.filter(rec => qualifiers.contains(rec))
    val noQualifiers:Array[String] = keywords.filter(rec => !qualifiers.contains(rec))
    val result:Tuple2[Array[String],Array[String]] = Tuple2[Array[String],Array[String]](needsQualifiers,noQualifiers)
    result
  }

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
    * method to generate a random Long
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
    * @param chars = list of characters that can be in the string
    * @return - string
    */
  def randomAlphaNumeric(size: Int=10, chars:String = Configuration.ALPHA_NUMERIC_STRING): String = {
    val builder = new StringBuilder
    var count = size
    while(count>0) {
      val character = (Math.random * chars.length).asInstanceOf[Int]
      builder.append(chars.charAt(character))
      count-=1
    }
    builder.toString
  }

  def determineRoundingType(roundingType:String):Value = {
    var result:Value = BigDecimal.RoundingMode.HALF_UP
    if(roundingType!=null) {
      if (roundingType.equals("roundup")) {
        result = BigDecimal.RoundingMode.UP
      } else if (roundingType.equals("rounddown")) {
        result = BigDecimal.RoundingMode.DOWN
      }
    }
    result
  }
  /**
    * method to generate a random Double value
    * @param min = lowest possile value
    * @param max = highest possible value
    * @param precision = precision after the decimal point
    * @param roundingType = how to round the result
    * @return Double
    */
  def randomDouble(min: Double ,max: Double, precision:Int,roundingType:String):Double = {
    val rand = new Random
    val generatedDouble: Double = rand.nextDouble() * (max - min) + min
    val roundedDouble = BigDecimal(generatedDouble).setScale(precision, determineRoundingType(roundingType)).toDouble
    roundedDouble
  }

  /**
    * method to generate a random Float value
    * @param min = lowest possile value
    * @param max = highest possible value
    * @param precision = precision after the decimal point
    * @param roundingType = how to round the result
    * @return Float
    */
  def randomFloat(min: Float ,max: Float, precision:Int, roundingType:String):Float = {
    val rand = new Random
    val generatedFloat:Float = rand.nextFloat()*(max-min)+min
    val roundedFloat:Float = BigDecimal(generatedFloat).setScale(precision, determineRoundingType(roundingType)).toFloat
    roundedFloat
  }

  // TODO - fix this, create function defined as a method
  //val randomMoney = randomFloat(min: Float ,max: Float, 2, roundingType:String)

  /**
    *
    * @param start
    * @param end
    * @param format
    * @return
    */
  def randomDate(start:String, end:String, format:String):String = {
    val dateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(format)
    val startDate:LocalDate = LocalDate.parse(start,dateFormatter)
    val endDate:LocalDate = LocalDate.parse(end,dateFormatter)
    val diffInDays:Long = ChronoUnit.DAYS.between(startDate,endDate)
    val rand = new Random
    val generatedDays:Long = BigDecimal(rand.nextFloat()*diffInDays).setScale(0,BigDecimal.RoundingMode.HALF_UP).toBigInt().intValue()
    val returnDate:LocalDate = startDate.plusDays(generatedDays)
    returnDate.format(dateFormatter)
  }

  /**
    *
    * @param format
    * @return
    */
  def randomDate(format:String):String = {
    val start:String = Configuration.DEFAULT_START_DATE
    val end:String = Configuration.DEFAULT_END_DATE
    val defaultDateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(Configuration.DEFAULT_DATE_FORMAT)
    val dateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(format)
    val startDate:LocalDate = LocalDate.parse(start,defaultDateFormatter)
    val endDate:LocalDate = LocalDate.parse(end,defaultDateFormatter)
    val diffInDays:Long = ChronoUnit.DAYS.between(startDate,endDate)
    val rand = new Random
    val generatedDays:Int = BigDecimal(rand.nextFloat()*diffInDays).setScale(0,BigDecimal.RoundingMode.HALF_UP).toBigInt().intValue()
    val returnDate:LocalDate = startDate.plusDays(generatedDays)
    returnDate.format(dateFormatter)
  }

  /**
    *
    * @param end
    * @param format
    * @return
    */
  def randomDateNoStart(end:String, format:String):String = {
    val start:String = Configuration.DEFAULT_START_DATE
    val defaultDateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(Configuration.DEFAULT_DATE_FORMAT)
    val startDate:LocalDate = LocalDate.parse(start,defaultDateFormatter)
    val dateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(format)
    val endDate:LocalDate = LocalDate.parse(end,dateFormatter)
    val diffInDays:Long = ChronoUnit.DAYS.between(startDate,endDate)
    val rand = new Random
    val generatedDays:Int = BigDecimal(rand.nextFloat()*diffInDays).setScale(0,BigDecimal.RoundingMode.HALF_UP).toBigInt().intValue()
    val returnDate:LocalDate = startDate.plusDays(generatedDays)
    returnDate.format(dateFormatter)
  }

  /**
    *
    * @param start
    * @param format
    * @return
    */
  def randomDateNoEnd(start:String, format:String):String = {
    val end:String = Configuration.DEFAULT_END_DATE
    val defaultDateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(Configuration.DEFAULT_DATE_FORMAT)
    val endDate:LocalDate = LocalDate.parse(end,defaultDateFormatter)
    val dateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(format)
    val startDate:LocalDate = LocalDate.parse(start,dateFormatter)
    val diffInDays:Long = ChronoUnit.DAYS.between(startDate,endDate)
    val rand = new Random
    val generatedDays:Int = BigDecimal(rand.nextFloat()*diffInDays).setScale(0,BigDecimal.RoundingMode.HALF_UP).toBigInt().intValue()
    val returnDate:LocalDate = startDate.plusDays(generatedDays)
    returnDate.format(dateFormatter)
  }

  /**
    *
    * @param start
    * @param end
    * @param format
    * @return
    */
  def randomDateTime(start:String, end:String, format:String):String = {
    val dateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(format)
    val startDateTime:LocalDateTime = LocalDateTime.parse(start,dateFormatter)
    val endDateTime:LocalDateTime = LocalDateTime.parse(end,dateFormatter)
    val diffInSeconds:Long = Duration.between(startDateTime,endDateTime).getSeconds
    val rand = new Random
    val generatedSeconds:Long = BigDecimal(rand.nextFloat()*diffInSeconds).setScale(0,BigDecimal.RoundingMode.HALF_UP).longValue()
    val returnDate:LocalDateTime = startDateTime.plusSeconds(generatedSeconds)
    returnDate.format(dateFormatter)
  }

  /**
    *
    * @param format
    * @return
    */
  def randomDateTime(format:String):String = {
    val start:String = Configuration.DEFAULT_START_DATE_TIME
    val end:String = Configuration.DEFAULT_END_DATE_TIME
    val defaultDateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(Configuration.DEFAULT_DATE_TIME_FORMAT)
    val dateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(format)
    val startDateTime:LocalDateTime = LocalDateTime.parse(start,defaultDateFormatter)
    val endDateTime:LocalDateTime = LocalDateTime.parse(end,defaultDateFormatter)
    val diffInSeconds:Long = Duration.between(startDateTime,endDateTime).getSeconds
    val rand = new Random
    val generatedSeconds:Long = BigDecimal(rand.nextFloat()*diffInSeconds).setScale(0,BigDecimal.RoundingMode.HALF_UP).longValue()
    val returnDate:LocalDateTime = startDateTime.plusSeconds(generatedSeconds)
    returnDate.format(dateFormatter)
  }

  /**
    *
    * @param end
    * @param format
    * @return
    */
  def randomDateTimeNoStart(end:String, format:String):String = {
    val start:String = Configuration.DEFAULT_START_DATE_TIME
    val defaultDateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(Configuration.DEFAULT_DATE_TIME_FORMAT)
    val dateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(format)
    val startDateTime:LocalDateTime = LocalDateTime.parse(start,defaultDateFormatter)
    val endDateTime:LocalDateTime = LocalDateTime.parse(end,dateFormatter)
    val diffInSeconds:Long = Duration.between(startDateTime,endDateTime).getSeconds
    val rand = new Random
    val generatedSeconds:Long = BigDecimal(rand.nextFloat()*diffInSeconds).setScale(0,BigDecimal.RoundingMode.HALF_UP).longValue()
    val returnDate:LocalDateTime = startDateTime.plusSeconds(generatedSeconds)
    returnDate.format(dateFormatter)
  }

  /**
    *
    * @param start
    * @param format
    * @return
    */
  def randomDateTimeNoEnd(start:String, format:String):String = {
    val end:String = Configuration.DEFAULT_END_DATE_TIME
    val defaultDateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(Configuration.DEFAULT_DATE_TIME_FORMAT)
    val dateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(format)
    val startDateTime:LocalDateTime = LocalDateTime.parse(start,dateFormatter)
    val endDateTime:LocalDateTime = LocalDateTime.parse(end,defaultDateFormatter)
    val diffInSeconds:Long = Duration.between(startDateTime,endDateTime).getSeconds
    val rand = new Random
    val generatedSeconds:Long = BigDecimal(rand.nextFloat()*diffInSeconds).setScale(0,BigDecimal.RoundingMode.HALF_UP).longValue()
    val returnDate:LocalDateTime = startDateTime.plusSeconds(generatedSeconds)
    returnDate.format(dateFormatter)
  }

  /**
    * method that will simply return the current date in whatever format is specified
    * by the user
    * @param format - pattern to format the date by
    * @return - string
    */
  def generateNowDate(format:String = Configuration.DEFAULT_DATE_FORMAT):String = {
    val dateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(format)
    val currentDateTime:LocalDate = DateUtils.now()
    currentDateTime.format(dateFormatter)
  }

  /**
    * method that will return the current date and time in whatever format is specified
    * by the user
    * @param format - pattern to format the date by
    * @return - string
    */
  def generateNowDateTime(format:String = Configuration.DEFAULT_NOW_DATE_TIME_FORMAT):String = {
    val dateFormatter:DateTimeFormatter = DateTimeFormatter.ofPattern(format)
    val currentDateTime:LocalDateTime = DateUtils.nowDateTime()
    currentDateTime.format(dateFormatter)
  }
  /**
    * method to pad any number that has already been converted to a string
    * with zeros
    * @param input - number to pad
    * @param length - final length
    * @return - String
    */
  def padNumberWithZeros(input:String, length:Int):String = {
    val builder = new StringBuilder()
    // if input is a decimal, we always want to pad to the right
    if(input.contains(".")) {
      builder.append(input)
      if(input.startsWith("-")) {
        if (input.length-1 < length) {
          for (i <- input.length-1 until length) {
            builder.append("0")
          }
        }
      } else {
        if (input.length < length) {
          for (i <- input.length until length) {
            builder.append("0")
          }
        }
      }
    } else {
      // otherwise, we want to append to the left
      if(input.startsWith("-")) {
        // if it is a negative
        // do we even need to add to it?
        if (input.length-1 < length) {
          // yes
          builder.append("-")
          for (i <- input.length-1 until length) {
            builder.append("0")
          }
          builder.append(input.replace("-",""))
        }
        else {
          // no, we don't need to append to it
          builder.append(input)
        }
      } else {
        // it is not a negative
        // do we need to append to it?
        if (input.length < length) {
          // yes
          for (i <- input.length until length) {
            builder.append("0")
          }
          builder.append(input)
        }
        else {
          // no, we don't need to append to it
          builder.append(input)
        }
      }
    }
    builder.toString
  }

  /**
    * method that pads a money value with zeros to 2 decimal places
    * @param input - input string to pad
    * @return - padded string
    */
  def padMoneyWithZeros(input:String):String = {
    var result:String = input
    val totalLength:Int = input.length
    val decimalLocation:Int = input.indexOf(".")
    if(decimalLocation == -1) {
      result = result.concat(".00")
    }
    else if(decimalLocation == totalLength-1) {
      result = result.concat("00")
    }
    else if(decimalLocation == totalLength-2) {
      result = result.concat("0")
    }
    result
  }
}
