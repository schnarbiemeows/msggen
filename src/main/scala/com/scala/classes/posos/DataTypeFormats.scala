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
    "RangedString" -> Set("length","chars","upper","lower"),
    "EnumInt" -> Set(),
    "RandomInt" -> Set("length","min","max"),
    "ExternalInt" -> Set(),
    "RangedInt" -> Set("length","min","max"),
    "EnumLong" -> Set(),
    "RandomLong" -> Set("length","min","max"),
    "ExternalLong" -> Set(),
    "RangedLong" -> Set("length","min","max"),
    "EnumFloat" -> Set(),
    "RandomFloat" -> Set("length","signDigits","min","max","roundup","rounddown","roundhalf"),
    "ExternalFloat" -> Set(),
    "RangedFloat" -> Set("length","signDigits","min","max","roundup","rounddown","roundhalf"),
    "EnumDouble" -> Set(),
    "RandomDouble" -> Set("length","signDigits","min","max","roundup","rounddown","roundhalf"),
    "ExternalDouble" -> Set(),
    "RangedDouble" -> Set("length","signDigits","min","max","roundup","rounddown","roundhalf"),
    "EnumDate" -> Set(),
    "RandomDate" -> Set("start","end","format"),
    "ExternalDate" -> Set(),
    "RangedDate" -> Set("start","end","format"),
    "EnumDateTime" -> Set(),
    "RandomDateTime" -> Set("start","end","format"),
    "ExternalDateTime" -> Set(),
    "RangedDateTime" -> Set("start","end","format"),
    "EnumMoney" -> Set(),
    "RandomMoney" -> Set("length","min","max","roundup","rounddown","roundhalf"),
    "ExternalMoney" -> Set(),
    "RangedMoney" -> Set("length","min","max","roundup","rounddown","roundhalf")
  )

  val keysThatNeedQualifiers:Map[String,Set[String]] = Map("EnumString" -> Set(),
    "RandomString" -> Set("length","chars"),
    "ExternalString" -> Set(),
    "RangedString" -> Set("length","chars"),
    "EnumInt" -> Set(),
    "RandomInt" -> Set("length","min","max"),
    "ExternalInt" -> Set(),
    "RangedInt" -> Set("length","min","max"),
    "EnumLong" -> Set(),
    "RandomLong" -> Set("length","min","max"),
    "ExternalLong" -> Set(),
    "RangedLong" -> Set("length","min","max"),
    "EnumFloat" -> Set(),
    "RandomFloat" -> Set("length","signDigits","min","max"),
    "ExternalFloat" -> Set(),
    "RangedFloat" -> Set("length","signDigits","min","max"),
    "EnumDouble" -> Set(),
    "RandomDouble" -> Set("length","signDigits","min","max"),
    "ExternalDouble" -> Set(),
    "RangedDouble" -> Set("length","signDigits","min","max"),
    "EnumDate" -> Set(),
    "RandomDate" -> Set("start","end","format"),
    "ExternalDate" -> Set(),
    "RangedDate" -> Set("start","end","format"),
    "EnumDateTime" -> Set(),
    "RandomDateTime" -> Set("start","end","format"),
    "ExternalDateTime" -> Set(),
    "RangedDateTime" -> Set("start","end","format"),
    "EnumMoney" -> Set(),
    "RandomMoney" -> Set("length","min","max"),
    "ExternalMoney" -> Set(),
    "RangedMoney" -> Set("length","min","max")
  )


}
