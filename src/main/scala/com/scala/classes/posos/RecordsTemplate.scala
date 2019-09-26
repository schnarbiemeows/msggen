/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

import scala.collection.mutable.ArrayBuffer

/**
  * this trait is a template for a list of records that we want
  * to generate random data to be used for a streaming process. The data for the records
  * are in an external file referenced as mode4.sourcefile in the config.properties
  */
trait RecordsTemplate {

  /**
    * the names of the fields we are generating records for
    */
  var fields:Array[String]
  /**
    * the data types of the fields we are generating records for
    * types are:
    * regular types - String, Int, Long, Float, Double, Boolean, LocalDate, LocalTime
    * the types below are special, and represent references to another
    * external file(mode4.externalfile in config.properties) which contain the
    * actual range of values the field could have
    * ExternalString
    * ExternalInt
    * ExternalLong
    * ExternalFloat
    * ExternalDouble
    * these fields below will not have possible values given in the mode4.sourcefile
    * instead, the program uses methods in the StringUtils, NumUtility, and DateUtils
    * classes to generate random values
    * RandomString
    * RandomInt
    * RandomLong
    * RandomFloat
    * RandomDouble
    * RandomLocalDate
    * RandomLocalTime
    */
  var dataTypes:Array[String]
  /**
    * any special formatting we need
    */
  var dataFormats:Array[String]
  /**
    * all of the possible values that each field could have
    */
  var dataQualifiers:Array[ArrayBuffer[String]]
  /**
    * the current array of records
    */
  var records:ArrayBuffer[Record]

  /**
    * location of our source file
    */
  val sourcefile:String
  /**
    * location of any external source file, will be NONE if we have none
    */
  val externalFile:String
  /**
    * number of total files to make
    */
  val numfiles:Int
  /**
    * number of records in each file
    */
  val numrecords:Int
  /**
    * output folder to locate the generated files
    */
  val outputFileFolder:String


}
