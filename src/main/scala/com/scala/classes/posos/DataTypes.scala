/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

object DataTypes {

  /**
    * these fields below will have a list of possible values below them, with "NONE" specified as the data format
    * so, the user is specifying the possible values for these fields
     */
  val validEnums:Set[String] = Set("EnumString", "EnumInt", "EnumLong" , "EnumFloat", "EnumDouble", "EnumDate", "EnumTime", "EnumMoney")
  /**
    * for these data types, the program will generate a pure random value of the particular data type
    * user will specify any format
    * for the values, user will specify:
    * RandomString : length of the String
    * RandomInt, RandomLong, RandomFloat, RandomDouble : min value followed by max value
    * RandomDate : start date followed by end date
    * RandomTime : start time followed by end time
    * RandomMoney : minimum followed my maximum amount
    */
  val validRandoms:Set[String] = Set("RandomString", "RandomInt", "RandomLong" , "RandomFloat", "RandomDouble", "RandomDate", "RandomTime", "RandomMoney")
  /**
    * for these data types, the user will be supplying a path to an external file which will have the values for
    * that particular data type. These fields are used like foreign key links to other tables
    * for the format, user will specify "NONE"
    * for the values, user will specify the absolute path to the file, keeping in mind the operating system
    */
  val validExternals:Set[String] = Set("ExternalString", "ExternalInt", "ExternalLong" , "ExternalFloat", "ExternalDouble", "ExternalDate", "ExternalTime", "ExternalMoney")
  /**
    * not currently being used
    */
  val validRanges:Set[String] = Set("RangedString", "RangedInt", "RangedLong" , "RangedFloat", "RangedDouble", "RangedDate", "RangedTime", "RangedMoney")

  def isValidDataType(dataType: String):Boolean = {
    validEnums.contains(dataType)||validRandoms.contains(dataType)||
      validExternals.contains(dataType)||validRanges.contains(dataType)
  }
}
