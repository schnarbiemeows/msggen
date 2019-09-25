/*
 * Created 2019 by Dylan Kessler
 */

/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import scala.collection.mutable.ArrayBuffer

/**
  * class for generating random LocalDateTime strings
  */
object DateTimeTypeGenerator {

  /**
    * this method generates a random EnumDateTime
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeEnumDateTime(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * this method generates a random RandomDateTime
    * @param formatString - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRandomDateTime(formatString: String, qualifiers:ArrayBuffer[String]):String = {
    var endWasSpecified:Boolean = false
    var startWasSpecified:Boolean = false
    var format:String = null
    var start:String = null
    var end:String = null
    var result:String = null
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers("RandomDateTime", formatString)._1
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
      }
      if(startWasSpecified&&endWasSpecified) {
        result = randomDateTime(start,end,format)
      } else {
        if(startWasSpecified) {
          result = randomDateTimeNoEnd(start,format)
        } else if(endWasSpecified) {
          result = randomDateTimeNoStart(end,format)
        } else {
          result = randomDateTime(format)
        }
      }
    }
    result
  }

  /**
    * this method generates a random ExternalDateTime
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeExternalDateTime(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * this method generates a random RangedDateTime
    * @param formatString - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRangedDateTime(formatString: String,qualifiers:ArrayBuffer[String]):String = {
    var endWasSpecified:Boolean = false
    var startWasSpecified:Boolean = false
    var format:String = null
    var start:String = null
    var end:String = null
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers("RangedDateTime", formatString)._1
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
      }
    }
    randomDateTime(start,end,format)
  }
}
