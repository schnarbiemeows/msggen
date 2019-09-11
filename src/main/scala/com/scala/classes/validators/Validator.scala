/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import java.io.File

import com.scala.classes.posos.DataTypeFormats
import com.scala.classes.utilities.Configuration


/**
  * trait for the handling of various validations
  */
trait Validator {

  /**
    * main validation method
    * @return
    */
  def validate():Boolean

  /**
    * this method will search the DataTypeFormats.keywords Map according to the
    * dataTypeName key, and make sure that all of the keywords listed in the format
    * string are found in the Set for that data type
    * @param dataTypeName
    * @param format
    * @return
    */
  def validateFormat(dataTypeName:String, format:String):Tuple2[Boolean,String] = {
    var isValidated = true
    var message:String = "NONE"
    val formatSet:Set[String] = DataTypeFormats.keywords(dataTypeName)
    if (format != "NONE") {
      val keywordsSpecified = format.split(Configuration.DELIMITTER1)
      for(key <- keywordsSpecified) {
        if (!formatSet.contains(key)) {
          isValidated = false
          message = s"key ${key} given is not an allowable key"
        }
      }
    }
    (isValidated,message)
  }

  /**
    * this method will filter out any keywords in the format string that do not need
    * the return array should index the same as the values array, so returnArray(0)
    * will correspond to dataValues(x)(0),for example
    * @param dataTypeName
    * @param format
    * @return
    */
  def filterQualifiers(dataTypeName:String, format:String):Array[String] = {
    val keywords:Array[String] = format.split(Configuration.DELIMITTER1)
    val qualifiers:Set[String] = DataTypeFormats.keysThatNeedQualifiers(dataTypeName)
    var returnArray:Array[String] = keywords.filter(rec => qualifiers.contains(rec))
    returnArray
  }

  /**
    * this method is used for all External data types
    * this method will test to see if the specified file actually exists
    * it makes no assumptions as to whether the path was correctly specified
    * for the particular operating system
    * @param filename = full path name to the file
    * @return
    */
  def valdateFileExists(filename:String):Boolean = {
    var exists = false
    val f = new File(filename)
    if(f.isFile) {
      exists = true
    }
    exists
  }
}
