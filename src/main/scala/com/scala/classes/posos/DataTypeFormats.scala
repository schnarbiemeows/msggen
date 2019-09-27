/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

/**
  * this object keeps track of all of the different types of
  * keywords that can be specified for each data type's data format
  * cell(row 3).
  */
object DataTypeFormats {

  /**
    * TODO - scaladoc this
    */


  
  val keywords:Map[String,Set[String]] = Map("EnumString" -> Set(),
    "RandomString" -> Set("length","chars","upper","lower"),
    "ExternalString" -> Set(),
    "EnumInt" -> Set(),
    "RandomInt" -> Set("length","min","max"),
    "ExternalInt" -> Set(),
    "EnumLong" -> Set(),
    "RandomLong" -> Set("length","min","max"),
    "ExternalLong" -> Set(),
    "EnumFloat" -> Set(),
    "RandomFloat" -> Set("length","signDigits","min","max","roundup","rounddown","roundhalf"),
    "ExternalFloat" -> Set(),
    "EnumDouble" -> Set(),
    "RandomDouble" -> Set("length","signDigits","min","max","roundup","rounddown","roundhalf"),
    "ExternalDouble" -> Set(),
    "EnumDate" -> Set(),
    "RandomDate" -> Set("start","end","format","now"),
    "ExternalDate" -> Set(),
    "EnumDateTime" -> Set(),
    "RandomDateTime" -> Set("start","end","format","now"),
    "ExternalDateTime" -> Set(),
    "EnumMoney" -> Set(),
    "RandomMoney" -> Set("min","max","roundup","rounddown","roundhalf"),
    "ExternalMoney" -> Set()
  )

  val keysThatNeedQualifiers:Map[String,Set[String]] = Map("EnumString" -> Set(),
    "RandomString" -> Set("length","chars"),
    "ExternalString" -> Set(),
    "EnumInt" -> Set(),
    "RandomInt" -> Set("length","min","max"),
    "ExternalInt" -> Set(),
    "EnumLong" -> Set(),
    "RandomLong" -> Set("length","min","max"),
    "ExternalLong" -> Set(),
    "EnumFloat" -> Set(),
    "RandomFloat" -> Set("length","signDigits","min","max"),
    "ExternalFloat" -> Set(),
    "EnumDouble" -> Set(),
    "RandomDouble" -> Set("length","signDigits","min","max"),
    "ExternalDouble" -> Set(),
    "EnumDate" -> Set(),
    "RandomDate" -> Set("start","end","format"),
    "ExternalDate" -> Set(),
    "EnumDateTime" -> Set(),
    "RandomDateTime" -> Set("start","end","format"),
    "ExternalDateTime" -> Set(),
    "EnumMoney" -> Set(),
    "RandomMoney" -> Set("min","max"),
    "ExternalMoney" -> Set()

  )


}
