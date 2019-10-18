/*
 * Created 2019 by Dylan Kessler
 */

/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import com.scala.classes.utilities.Configuration

import scala.collection.mutable.ArrayBuffer

/**
  * class for generating random LocalDateTime strings
  */
object DateTimeTypeGenerator extends Generator {

  /**
    * this method generates a random RandomDateTime
    * @param formatString - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRandomDateTime(formatString: String, qualifiers:ArrayBuffer[String]):String = {
    var endWasSpecified: Boolean = false
    var startWasSpecified: Boolean = false
    var nowSpecified: Boolean = false
    var format: String = null
    var start: String = null
    var end: String = null
    var result: String = null
    var nullPercentage:Double = 0.0
    val splitLists: Tuple2[Array[String], Array[String]] = filterQualifiers("RandomDateTime", formatString)
    val formatsThatNeedQualifierChecks: Array[String] = splitLists._1
    val formatsNotNeedingQualifiers: Array[String] = splitLists._2
    for (i <- 0 until formatsNotNeedingQualifiers.length) {
      formatsNotNeedingQualifiers(i) match {
        case "now" => nowSpecified = true
        case _ => None
      }
    }
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "format" => {
          format = qualifiers(i)
        }
        case "start" => {
          startWasSpecified = true
          start = qualifiers(i)
        }
        case "end" => {
          endWasSpecified = true
          end = qualifiers(i)
        }
        case "nullable" => nullPercentage = qualifiers(i).toDouble
      }
    }
    if(nowSpecified) {
      if(format!=null) {
        result = generateNowDateTime(format)
      } else {
        result = generateNowDateTime()
      }
    }
    else
    {
      if(format==null) { format = Configuration.DEFAULT_DATE_TIME_FORMAT }
      if (startWasSpecified && endWasSpecified) {
        result = randomDateTime(start, end, format)
      } else {
        if (startWasSpecified) {
          result = randomDateTimeNoEnd(start, format)
        } else if (endWasSpecified) {
          result = randomDateTimeNoStart(end, format)
        } else {
          result = randomDateTime(format)
        }
      }
    }
    val randomNum = randomDouble(0,1,2,"rounddown")
    if(randomNum<nullPercentage) "" else result
  }
}
