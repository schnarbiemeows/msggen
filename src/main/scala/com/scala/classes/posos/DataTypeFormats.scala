/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

/**
  * this object keeps track of all of the different types of
  * rules that can be specified for each data type's data format
  * cell(row 3). for example, if you specified a dataType=EnumString
  * and the dataFormat = "length=10,upper", this would mean that each
  * random string should have a maximum length of 10,and be all
  * uppercase characters
  */
object DataTypeFormats {

  /**
    * TODO - scaladoc this
    */

  /**
    * specific explanation of rules:
    * '=' -> some rules(like length) are key-value pairs that
    * are separated by '='
    * for length: the value to the right of the '=' will always be an integer
    */
  val stringRuleKeys = Set("length","chars")
  val stringRuleKeywords = Set("upper","lower")

  val intRuleKeys = Set("length")
  val intRuleKeywords = Set()

  val longRuleKeys = Set("length")
  val longRuleKeywords = Set()

  val floatRuleKeys = Set("length","signDigits")
  val floatRuleKeywords = Set("roundup","rounddown","round")

  val doubleRuleKeys = Set("length","signDigits")
  val doubleRuleKeywords = Set("roundup","rounddown","round")

  val dateRuleKeys = Set("format")
  val dateRuleKeywords = Set()

  val timeRuleKeys = Set("format")
  val timeRuleKeywords = Set()

  val moneyRuleKeys = Set()
  val moneyRuleKeywords = Set()
}
