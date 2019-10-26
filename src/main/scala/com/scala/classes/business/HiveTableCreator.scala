/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import java.util.Properties

import com.scala.classes.posos.{DataTypes, RecordsTemplate}
import com.scala.classes.utilities.Configuration

/**
  * this class is used during modes 5, and will generate a Hive table creation
  * script using the field names and the data types from a RecordTemplate
  */
object HiveTableCreator {


  /**
    * main method to create the Hive script
    * @param template - Record template with the table data
    * @param properties - singleton Properties object
    * @return - List[String] of the text to be in the file
    */
  def makeTableArray(template:RecordsTemplate, properties:Properties):List[String] = {
    val databasename:String = properties.getProperty(Configuration.MODE5_HIVE_DATABASE_NAME)
    val tablename:String = properties.getProperty(Configuration.MODE5_HIVE_TABLE_NAME)
    val external:String = makeExternal(properties.getProperty(Configuration.MODE5_HIVE_EXTERNAL_TABLE))
    val location:String = properties.getProperty(Configuration.MODE5_HIVE_DATA_LOCATION)
    val hiveTypes = template.dataTypes.map(hiveMapper(_,properties.getProperty(Configuration.MODE5_HIVE_RAWDATA)))
    val closingparenthesisline = ")"
    val serde:String = makeserde(properties.getProperty(Configuration.MODE4_OUTPUT_FILE_TYPE))
    val serdeproperties:String = makeserdeproperties(properties.getProperty(Configuration.MODE4_OUTPUT_FILE_TYPE))
    val storedAs:String = makestoredAs(properties.getProperty(Configuration.MODE4_OUTPUT_FILE_TYPE))
    val locationStr = "location \'"+location+"\' ;"
    val count = determineCount(hiveTypes,serde)
    val hiveText:Array[String] = new Array[String](count)
    hiveText(0) = makefirstline(external,databasename,tablename)
    for(i <- 0 until hiveTypes.length) {
      if(i<hiveTypes.length-1) {
        hiveText(i+1) = template.fields(i) + " " + hiveTypes(i)+","
      } else {
        hiveText(i+1) = template.fields(i) + " " + hiveTypes(i)
      }
    }
    hiveText(hiveTypes.length+1) = closingparenthesisline
    if(serde.length>0) {
      hiveText(hiveTypes.length+2) = serde
      hiveText(hiveTypes.length+3) = serdeproperties
      if(external.length>0) {
        hiveText(hiveTypes.length+4) = storedAs
      } else {
        hiveText(hiveTypes.length+4) = storedAs +";"
      }
    } else {
      if(external.length>0) {
        hiveText(hiveTypes.length+2) = storedAs
      } else {
        hiveText(hiveTypes.length+2) = storedAs +";"
      }
    }
    if(external.length>0) {
      hiveText(hiveText.length-1) = locationStr
    } else {
      hiveText(hiveText.length-1) = makeloadline(external,databasename,tablename,location)
    }
    hiveText.toList
  }

  /**
    * variable
    * functions
    */
  val determineCount:(Array[String],String) => (Int) = (hiveTypes,serde) => { if(serde.length>0) hiveTypes.length+6 else hiveTypes.length+4 }
  val hiveMapper:(String,String) => (String) = (dataType,rawIndicator) => { if(rawIndicator=="true") "String" else DataTypes.hiveMappings.getOrElse(dataType,"String") }
  val makeExternal:(String) => (String) = (input) => { if(input=="true") "external" else "" }
  val makefirstline:(String,String,String) => (String) = (external,databasename,tablename) => { "create "+external+" table if not exists "+ databasename+"."+tablename+" (" }
  val makeserde:(String) => (String) = (input) => { if(input=="CSV") "ROW FORMAT SERDE \'org.apache.hadoop.hive.serde2.OpenCSVSerde\'" else "" }
  val makeserdeproperties:(String) => (String) = (input) => { if(input=="CSV") "WITH SERDEPROPERTIES ('separatorChar' = ',')" else "" }
  val makestoredAs:(String) => (String) = (input) => { if(input=="CSV") "stored as textfile" else "ROW FORMAT SERDE 'org.apache.hive.hcatalog.data.JsonSerDe' STORED AS TEXTFILE" }
  val makeloadline:(String,String,String,String) => (String) = (external,databasename,tablename,location) => {
    if(external.length==0) "load data inpath \'"+location+"\' into table "+databasename+"."+tablename + ";" else ""
  }
}
