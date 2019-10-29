/*
 * Created 2019 by Dylan Kessler
 */

/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import com.scala.classes.utilities.{Configuration, PropertyLoader}

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different Long types
  */
object WholeNumberTypeGenerator extends Generator {

  /**
    * this method generates a random Long number for both RandomLong and RandomInt
    * data types
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRandomWholeNumber(format: String, qualifiers:ArrayBuffer[String]):String = {
    var length:Int = 10
    var min:Long = 0
    var max:Long = 0
    var nullPercentage:Double = 0.0
    val splitLists:Tuple2[Array[String],Array[String]] = filterQualifiers("RandomLong", format)
    val formatsThatNeedQualifierChecks:Array[String] = splitLists._1
    //val formatsNotNeedingQualifiers = splitLists._2
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "length" => length = qualifiers(i).toInt
        case "min" => min = qualifiers(i).toLong
        case "max" => max = qualifiers(i).toLong
        case "nullable" => nullPercentage = qualifiers(i).toDouble
      }
    }
    val result = padNumberWithZeros(randomLong(min,max).toString,length)
    val randomNum = randomDouble(0,1,2,"rounddown")
    if(randomNum<nullPercentage) "" else result
  }

  def makeRangedWholeNumber(typeName:String, format: String, qualifiers:ArrayBuffer[String],arrayNum:Int, fileNum: Int):String = {
    var length:Int = 10
    var linbase:Long = 0
    var linadd:Long = 0
    var nullPercentage:Double = 0.0
    val splitLists:Tuple2[Array[String],Array[String]] = filterQualifiers(typeName, format)
    val formatsThatNeedQualifierChecks:Array[String] = splitLists._1
    //val formatsNotNeedingQualifiers = splitLists._2
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "length" => length = qualifiers(i).toInt
        case "linbase" => linbase = qualifiers(i).toLong
        case "linadd" => linadd = qualifiers(i).toLong
        case "nullable" => nullPercentage = qualifiers(i).toDouble
      }
    }
    val result = padNumberWithZeros(calculateLinearRangedNum(linbase,linadd,arrayNum,fileNum).toString,length)
    val randomNum = randomDouble(0,1,2,"rounddown")
    if(randomNum<nullPercentage) "" else result
  }

  /**
    * ths method calculates a linear value increment(like a primary key)
    * @param linbase - the initial base value; array index 0 of file # = 0 will have this value
    * @param linadd - an amount to incrementally add
    * @param arrayNum - the index in the array for this record
    * @param fileNum - what file number we are on
    * @return
    */
  def calculateLinearRangedNum(linbase:Long,linadd:Long,arrayNum:Long,fileNum:Long):Long = {
    val numRecordsPerFile:Long = PropertyLoader.getProperty(Configuration.MODE4_NUM_RECORDS).toLong
    linbase+linadd*fileNum*numRecordsPerFile+linadd*arrayNum
  }
}
