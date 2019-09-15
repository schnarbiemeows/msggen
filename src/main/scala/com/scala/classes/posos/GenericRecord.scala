/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

class GenericRecord(val flds: Array[String],val fldValues: Array[String]) extends Record {
  override val fields: Array[String] = flds
  override val fieldValues: Array[String] = fldValues

  override def toCSV():String = {
    var builder:StringBuilder = new StringBuilder()
    for(item <- fieldValues ) {builder.append(item).append(",")}
    var output:String = builder.toString()
    output.substring(0,output.length-1)
  }

  override def toJSON():String = {
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
