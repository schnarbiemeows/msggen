/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

import scala.collection.immutable.HashMap

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
    "RandomFloat" -> Set("length","signDigits","min","max","roundup","rounddown","round"),
    "ExternalFloat" -> Set(),
    "RangedFloat" -> Set("length","signDigits","min","max","roundup","rounddown","round"),
    "EnumDouble" -> Set(),
    "RandomDouble" -> Set("length","signDigits","min","max","roundup","rounddown","round"),
    "ExternalDouble" -> Set(),
    "RangedDouble" -> Set("length","signDigits","min","max","roundup","rounddown","round"),
    "EnumDate" -> Set(),
    "RandomDate" -> Set("startdate","enddate","format"),
    "ExternalDate" -> Set(),
    "RangedDate" -> Set("startdate","enddate","format"),
    "EnumTime" -> Set(),
    "RandomTime" -> Set("starttime","endtime","format"),
    "ExternalTime" -> Set(),
    "RangedTime" -> Set("starttime","endtime","format"),
    "EnumMoney" -> Set(),
    "RandomMoney" -> Set("length","min","max","roundup","rounddown","round"),
    "ExternalMoney" -> Set(),
    "RangedMoney" -> Set("length","min","max","roundup","rounddown","round")
  )

  val keysThatNeedQualifiers:Map[String,Set[String]] = Map("EnumString" -> Set(),
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
    "RandomFloat" -> Set("length","signDigits","min","max","roundup","rounddown","round"),
    "ExternalFloat" -> Set(),
    "RangedFloat" -> Set("length","signDigits","min","max","roundup","rounddown","round"),
    "EnumDouble" -> Set(),
    "RandomDouble" -> Set("length","signDigits","min","max","roundup","rounddown","round"),
    "ExternalDouble" -> Set(),
    "RangedDouble" -> Set("length","signDigits","min","max","roundup","rounddown","round"),
    "EnumDate" -> Set(),
    "RandomDate" -> Set("startdate","enddate","format"),
    "ExternalDate" -> Set(),
    "RangedDate" -> Set("startdate","enddate","format"),
    "EnumTime" -> Set(),
    "RandomTime" -> Set("starttime","endtime","format"),
    "ExternalTime" -> Set(),
    "RangedTime" -> Set("starttime","endtime","format"),
    "EnumMoney" -> Set(),
    "RandomMoney" -> Set("length","min","max","roundup","rounddown","round"),
    "ExternalMoney" -> Set(),
    "RangedMoney" -> Set("length","min","max","roundup","rounddown","round")
  )


}
