/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

import scala.collection.mutable.ArrayBuffer

trait Record {

  val fields:Array[String]
  val fieldValues:Array[String]
  val open:String = "{"
  val closed:String = "}"
  val quotes = "\""

  /**
    * this method will convert records that do NOT include complex fields(arrays, structures)
    * @throws java.lang.NullPointerException
    * @throws java.lang.Exception
    * @return json
    */
  @throws(classOf[NullPointerException])
  @throws(classOf[Exception])
  def toJsonSimple() :String = {
    var json:StringBuilder = new StringBuilder().append(open)
    if(fields==null||fieldValues==null) {
      throw new NullPointerException("fields or fieldValues array is null!")
    }
    if(fields.length!=fieldValues.length) {
      throw new Exception("field and field values arrays have different lengths!")
    }
    val fieldArrayLength = fields.length
    for(i <- 0 until fieldArrayLength-1) {
      json.append(quotes).append(fields(i)).append(quotes).append(":").
        append(quotes).append(fieldValues(i)).append(quotes).append(",")
    }
    json.append(quotes).append(fields(fieldArrayLength-1)).append(quotes).append(":").
      append(quotes).append(fieldValues(fieldArrayLength-1)).append(quotes).append(closed)
    json.toString()
  }
}
