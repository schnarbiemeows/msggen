/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

/**
  * object that contains lists of all of the different valid data types the
  * can be specified in row #2 of the input template
  */
object DataTypes {

  /**
    * these fields below will have a list of possible values below them, with "NONE" specified as the data format
    * so, the user is specifying the possible values for these fields
     */
  val validEnums:Set[String] = Set("EnumString", "EnumInt", "EnumLong" , "EnumFloat", "EnumDouble", "EnumDate", "EnumDateTime", "EnumMoney")
  /**
    * for these data types, the program will generate a pure random value of the particular data type
    * user will specify any format
    * for the values, user will specify:
    * RandomString : length of the String
    * RandomInt, RandomLong, RandomFloat, RandomDouble : min value followed by max value
    * RandomDate : start date followed by end date
    * RandomDateTime : start time followed by end time
    * RandomMoney : minimum followed my maximum amount
    */
  val validRandoms:Set[String] = Set("RandomString", "RandomInt", "RandomLong" , "RandomFloat", "RandomDouble", "RandomDate", "RandomDateTime", "RandomMoney")
  /**
    * for these data types, the user will be supplying a path to an external file which will have the values for
    * that particular data type. These fields are used like foreign key links to other tables
    * for the format, user will specify "NONE"
    * for the values, user will specify the absolute path to the file, keeping in mind the operating system
    */
  val validExternals:Set[String] = Set("ExternalString", "ExternalInt", "ExternalLong" , "ExternalFloat", "ExternalDouble", "ExternalDate", "ExternalDateTime", "ExternalMoney")

  /**
    * method to determine if a data type in the teplate is a valid one
    * @param dataType - data type specified in the template
    * @return - true or false
    */
  def isValidDataType(dataType: String):Boolean = {
    validEnums.contains(dataType)||validRandoms.contains(dataType)||
      validExternals.contains(dataType)
  }
}
