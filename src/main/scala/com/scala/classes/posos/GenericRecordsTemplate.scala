/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos
import java.util.Properties

import com.scala.classes.utilities.Configuration

import scala.collection.mutable.ArrayBuffer

class GenericRecordsTemplate(val properties:Properties) extends RecordsTemplate {
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
  var dataValues:Array[Array[String]] = _
  /**
    *
    */
  override var records: ArrayBuffer[Record] = _
  /**
    * location of our source file
    */
  override val sourcefile:String = properties.get(Configuration.MODE4_SRC_FILE).toString
  /**
    * location of any external source file, will be NONE if we have none
    */
  override val externalFile:String = properties.get(Configuration.MODE4_SRC_FILE).toString
  /**
    * number of total files to make
    */
  override val numfiles: Int = properties.get(Configuration.MODE4_NUM_FILES).toString.toInt
  /**
    * number of records in each file
    */
  override val numrecords: Int = properties.get(Configuration.MODE4_NUM_RECORDS).toString.toInt
  /**
    * output folder to locate the generated files
    */
  override val outputFileFolder:String = {
    var name:String = properties.get(Configuration.MODE4_OUTPUT_FILE).toString
    var os:String = properties.get(Configuration.SYSTEM).toString
    if(os.toUpperCase == "WINDOWS") {
      if(!name.endsWith("\\")) { name +="\\" }
    } else {
      if(!name.endsWith("/")) { name +="/" }
    }
    name
  }
  /**
    * filename prepender for the final name of each file
    */
  override val fileNamePrepender: String = properties.get(Configuration.MODE4_FILE_PREPENDER).toString
}
