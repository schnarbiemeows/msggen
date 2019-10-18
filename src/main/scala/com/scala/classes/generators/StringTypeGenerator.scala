/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import scala.collection.mutable.ArrayBuffer

/**
  * this object generates each of the different String types
  */
object StringTypeGenerator extends Generator{

  /**
    * this method generates a random RandomString
    * @param format - formatting specifications for the field
    * @param qualifiers - array of format qualifiers
    * @return - string
    */
  def makeRandomString(format: String,qualifiers:ArrayBuffer[String]):String = {
    var length:Int = 10
    var chars:String = null
    var toUpper:Boolean = false
    var toLower:Boolean = false
    var nullPercentage:Double = 0.0
    val splitLists:Tuple2[Array[String],Array[String]] = filterQualifiers("RandomString", format)
    val formatsThatNeedQualifierChecks:Array[String] = splitLists._1
    val formatsNotNeedingQualifiers = splitLists._2
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "length" => length = qualifiers(i).toInt
        case "chars" => chars = qualifiers(i).toString
        case "nullable" => nullPercentage = qualifiers(i).toDouble
      }
    }
    for (i <- 0 until formatsNotNeedingQualifiers.length) {
      formatsNotNeedingQualifiers(i) match {
        case "upper" => toUpper = true
        case "lower" => toLower = true
        case _ => None
      }
    }
    var result = if(chars==null) randomAlphaNumeric(length) else randomAlphaNumeric(length,chars)
    result = if(toUpper) result.toUpperCase else if(toLower) result.toLowerCase else result
    val randomNum = randomDouble(0,1,2,"rounddown")
    if(randomNum<nullPercentage) "" else result
  }
}
