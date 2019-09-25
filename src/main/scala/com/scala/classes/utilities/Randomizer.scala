/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

import java.time.LocalDate
import java.util.Properties
import java.util.concurrent.TimeUnit

import scala.util.Random

/**
  * this object is in charge of generating a manner of random items:
  * numbers, names, address information, etc..
  */
trait Randomizer {

  /**
    * initialize properties
    * @param props - program properties
    */
  def initialize(props: Properties)

  /**
    * method to generate a random alphanumeric String of a given length
    * @param size = length of the String
    * @return builder.toString
    */
  def randomAlphaNumeric(size: Int): String = {
    val builder = new StringBuilder
    while(size>0) {
      val character = (Math.random * Configuration.ALPHA_NUMERIC_STRING.length).asInstanceOf[Int]
      builder.append(Configuration.ALPHA_NUMERIC_STRING.charAt(character))
    }
    builder.toString
  }

  /**
    * method to implement a time delay in milliseconds
    * @param milliseconds - length of delay that we want
    */
  def timeDelay(milliseconds: Int): Unit = {
    try {
      LogUtil.msggenThread1LoggerDEBUG("sleeping for " + milliseconds + " milliseconds")
      TimeUnit.MILLISECONDS.sleep(milliseconds)
    } catch {
      case ex: InterruptedException =>
        Thread.currentThread.interrupt()
        LogUtil.msggenThread1LoggerDEBUG("sleep interrupted!")
    }
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
    * this method makes a binary true/false decision with a weight of true =
    * the probabilityOfTrue value passed in . It generates a random number between 0 and 1
    * and compares it to probabilityOfTrue. If its > probabilityOfTrue
    * then it returns false, otherwise true
    * @param probabilityOfTrue = % chance to be true
    * @return true or false
    */
  def makeBinaryDecision(probabilityOfTrue:Double):Boolean = {
    val rand = new Random
    val generatedValue:Double = rand.nextDouble()
    if(generatedValue>probabilityOfTrue) false
    true
  }

  /**
    * method to generate a random date of birth
    * @param minage = minimum age in days
    * @param maxage = maximum age in days
    * @return dobStr
    */
  def randomDOB(minage:Int, maxage:Int):String = {
    val rand = new Random
    val mindays = minage*365
    val maxdays = maxage*365
    val daystosubtract:Long = rand.nextInt((maxdays - mindays) + 1) + mindays
    val dob:LocalDate = DateUtils.addPeriodToLocalDate(DateUtils.now(),-daystosubtract)
    val dobStr = DateUtils.getStringFromDate(dob)
    dobStr
  }

  /**
    * method to generate a random date of birth relative to the primary member's date of birth
    * @param primaryDOB = date of birth of the primary
    * @param range = range (-range/2 + primaryDOB to primaryDOB + range/2) of the birthdate
    * @return dobStr
    */
  def randomDOBForSpouse(primaryDOB:String,range:Int):String = {
    val rand = new Random
    val format:String = "yyyy-MM-dd"
    val primDOB:LocalDate = DateUtils.getDateFromString(primaryDOB,format)
    val meanRange:Int = range*365/2
    val daystosubtract:Long = rand.nextInt(range*365 + 1) - meanRange
    val dob:LocalDate = DateUtils.addPeriodToLocalDate(primDOB,daystosubtract)
    val dobStr = DateUtils.getStringFromDate(dob)
    //println(s"DOB primary = ${primaryDOB},DOB local = ${primDOB},range = ${meanRange},daystosubtract = ${daystosubtract},  new DOB = ${dobStr}")
    dobStr
  }

  /**
    * method to generate a date of birth for a dependent between 0 and maxage
    * @param maxage = maximum age of dependent
    * @return dobStr
    */
  def randomDOBforChild(maxage:Int):String = {
    val rand = new Random
    val mindays = 0
    val maxdays = maxage*365
    val daystosubtract:Long = rand.nextInt((maxdays - mindays) + 1) + mindays
    val dob:LocalDate = DateUtils.addPeriodToLocalDate(DateUtils.now(),-daystosubtract)
    val dobStr = DateUtils.getStringFromDate(dob)
    dobStr
  }
}
