/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos
import com.scala.classes.utilities.{Configuration, PropertyLoader}

import scala.collection.mutable.ArrayBuffer

/**
  * this class contains all of the meta-data associated with a particular
  * request for data generation(a file or files of random data that we want to
  * generate). The class contains 3 different items:
  * 1. meta-data information about all of the fields in our data, what
  * the field names are, what data types they are, what their data formatting rules
  * are, and what all of the different possible values that each field could have
  * 2. assorted meta-data like how many files we want to make, how many records in
  * each file, etc..
  * 3. A list of all of the records that are generated

  */
class GenericRecordsTemplate() extends RecordsTemplate {
  /**
    * the names of the fields we are generating records for
    */
  override var fields: Array[String] = _
  /**
    * the data types of the fields we are generating records for
    * types are set/listed in the object DataTypes.scala:
    */
  override var dataTypes: Array[String] = _
  /**
    * any special formatting we need
    */
  override var dataFormats: Array[String] = _
  /**
    * all of the possible values that each field could have
    */
  var dataQualifiers:Array[ArrayBuffer[String]] = _
  /**
    *
    */
  override var records: ArrayBuffer[Record] = _
  /**
    * location of our source file
    */
  override val sourcefile:String = PropertyLoader.getProperty(Configuration.MODE4_SRC_FILE).toString
  /**
    * location of any external source file, will be NONE if we have none
    */
  override val externalFile:String = PropertyLoader.getProperty(Configuration.MODE4_SRC_FILE).toString
  /**
    * number of total files to make
    */
  override val numfiles: Int = PropertyLoader.getProperty(Configuration.MODE4_NUM_FILES).toString.toInt
  /**
    * number of records in each file
    */
  override val numrecords: Int = PropertyLoader.getProperty(Configuration.MODE4_NUM_RECORDS).toString.toInt
  /**
    * output folder to locate the generated files
    */
  override val outputFileFolder:String = {
    val name:String = PropertyLoader.getProperty(Configuration.MODE4_OUTPUT_FILE).toString
    val os:String = PropertyLoader.getProperty(Configuration.SYSTEM).toString
    val output:String = result(os,name)
    output
  }

  /**
    * method that will add either "\\" or "/" to the end of a
    * filepath depending on the operating system
    * @param os - operating system
    * @param name - file path
    * @return - String
    */
  def result(os:String,name:String):(String) = {
    if (os.toUpperCase == "WINDOWS") {
      if (!name.endsWith("\\")) {
        name + "\\"
      }
      else name
    }
    else {
      if (!name.endsWith("/")) {
        name + "/"
      } else name
    }
  }
}
