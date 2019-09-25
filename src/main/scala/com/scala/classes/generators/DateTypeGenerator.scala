/*
 * Created 2019 by Dylan Kessler
 */

/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import scala.collection.mutable.ArrayBuffer

/**
  * class for generating random LocalDate strings
  */
object DateTypeGenerator {

  /**
    * this method generates a random EnumDate
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeEnumDate(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * this method generates a random RandomDate
    * @param formatString - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRandomDate(formatString: String, qualifiers:ArrayBuffer[String]):String = {
    var endWasSpecified:Boolean = false
    var startWasSpecified:Boolean = false
    var format:String = null
    var start:String = null
    var end:String = null
    var result:String = null
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers("RandomDate", formatString)._1
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
        result = randomDate(start,end,format)
      } else {
        if(startWasSpecified) {
          result = randomDateNoEnd(start,format)
        } else if(endWasSpecified) {
          result = randomDateNoStart(end,format)
        } else {
          result = randomDate(format)
        }
      }
    }
    result
  }

  /**
    * this method generates a random ExternalDate
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeExternalDate(qualifiers:ArrayBuffer[String]):String = {
    var arrayLength = qualifiers.length
    var index = randomInteger(0,arrayLength)
    qualifiers(index)
  }

  /**
    * this method generates a random RangedDate
    * @param formatString - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRangedDate(formatString: String, qualifiers:ArrayBuffer[String]):String = {
    var endWasSpecified:Boolean = false
    var startWasSpecified:Boolean = false
    var format:String = null
    var start:String = null
    var end:String = null
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers("RangedDate", formatString)._1
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
    randomDate(start,end,format)
  }
}
