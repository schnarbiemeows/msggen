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


  
  val keywords:Map[String,Set[String]] = Map("EnumString" -> Set("nullable"),
    "RandomString" -> Set("length","chars","upper","lower","nullable"),
    "ExternalString" -> Set("nullable"),
    "EnumInt" -> Set("nullable"),
    "RandomInt" -> Set("length","min","max","nullable"),
    "ExternalInt" -> Set("nullable"),
    "EnumLong" -> Set("nullable"),
    "RandomLong" -> Set("length","min","max","nullable"),
    "ExternalLong" -> Set("nullable"),
    "EnumFloat" -> Set("nullable"),
    "RandomFloat" -> Set("length","signDigits","min","max","roundup","rounddown","roundhalf","nullable"),
    "ExternalFloat" -> Set("nullable"),
    "EnumDouble" -> Set("nullable"),
    "RandomDouble" -> Set("length","signDigits","min","max","roundup","rounddown","roundhalf","nullable"),
    "ExternalDouble" -> Set("nullable"),
    "EnumDate" -> Set("nullable"),
    "RandomDate" -> Set("start","end","format","now","nullable"),
    "ExternalDate" -> Set("nullable"),
    "EnumDateTime" -> Set("nullable"),
    "RandomDateTime" -> Set("start","end","format","now","nullable"),
    "ExternalDateTime" -> Set("nullable"),
    "EnumMoney" -> Set("nullable"),
    "RandomMoney" -> Set("min","max","roundup","rounddown","roundhalf","nullable"),
    "ExternalMoney" -> Set("nullable")
  )

  val keysThatNeedQualifiers:Map[String,Set[String]] = Map("EnumString" -> Set("nullable"),
    "RandomString" -> Set("length","chars","nullable"),
    "ExternalString" -> Set("nullable"),
    "EnumInt" -> Set("nullable"),
    "RandomInt" -> Set("length","min","max","nullable"),
    "ExternalInt" -> Set("nullable"),
    "EnumLong" -> Set("nullable"),
    "RandomLong" -> Set("length","min","max","nullable"),
    "ExternalLong" -> Set("nullable"),
    "EnumFloat" -> Set("nullable"),
    "RandomFloat" -> Set("length","signDigits","min","max","nullable"),
    "ExternalFloat" -> Set("nullable"),
    "EnumDouble" -> Set("nullable"),
    "RandomDouble" -> Set("length","signDigits","min","max","nullable"),
    "ExternalDouble" -> Set("nullable"),
    "EnumDate" -> Set("nullable"),
    "RandomDate" -> Set("start","end","format","nullable"),
    "ExternalDate" -> Set("nullable"),
    "EnumDateTime" -> Set("nullable"),
    "RandomDateTime" -> Set("start","end","format","nullable"),
    "ExternalDateTime" -> Set("nullable"),
    "EnumMoney" -> Set("nullable"),
    "RandomMoney" -> Set("min","max","nullable"),
    "ExternalMoney" -> Set("nullable")
  )


}
