/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

/**
  * trait that represents a record(or Row) in a data file(or table)
  */
trait Record {

  val fields:Array[String]
  val fieldValues:Array[String]
  val open:String = "{"
  val closed:String = "}"
  val quotes = "\""

  /**
    * method that will return the rows values as a comma separated String
    * @return - String
    */
  def toCSV():String = {
    var builder:StringBuilder = new StringBuilder()
    for(item <- fieldValues ) {builder.append(item).append(",")}
    var output:String = builder.toString()
    output.substring(0,output.length-1)
  }

  /**
    * method that will retrun the fields and values as a JSON string
    * @return - String
    */
  def toJSON():String = {
    var builder:StringBuilder = new StringBuilder().append("{ ")
    var count:Int = 0
    while(count<fields.length) {
      if(count==0) {
        builder.append("\"").append(fields(count)).append("\" : \"").append(fieldValues(count)).append("\" ")
      } else {
        builder.append(", \"").append(fields(count)).append("\" : \"").append(fieldValues(count)).append("\" ")
      }
      count+=1
    }
    builder.append("}").toString
  }
}
