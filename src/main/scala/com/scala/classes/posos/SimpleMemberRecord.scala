/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos


/**
  * class of SimpleMemberRecord
  */
class SimpleMemberRecord {

  var accountId: Option[String] = None
  var socialSecurityNumber: Option[String] = None
  var subscriberId: Option[String] = None
  var dependentNumber: Option[String] = None
  var alphaPrefix: Option[String] = None
  var gender: Option[String] = None
  var dob: Option[String] = None
  var firstName: Option[String] = None
  var middleName: Option[String] = None
  var lastName: Option[String] = None

  /**
    * this method will return the record as a csv string
    * @return - csv string
    */
  def toCSV():String = {
    s"${accountId.getOrElse("")},${subscriberId.getOrElse("")},${alphaPrefix.getOrElse("")}," +
      s"${dependentNumber.getOrElse("")},${socialSecurityNumber.getOrElse("")},${firstName.getOrElse("")}," +
      s"${middleName.getOrElse("")},${lastName.getOrElse("")},${gender.getOrElse("")},${dob.getOrElse("")}"
  }

  def toJSON():String = {
    s"""{ "accountId" : "${accountId.getOrElse("")}" , "subscriberId" : "${subscriberId.getOrElse("")}" , "alphaPrefix" : "${alphaPrefix.getOrElse("")}" , "dependentNumber" : "${dependentNumber.getOrElse("")}" , "socialSecurityNumber" : "${socialSecurityNumber.getOrElse("")}" , "firstName" : "${firstName.getOrElse("")}" , "middleName" : "${middleName.getOrElse("")}" , "lastName" : "${lastName.getOrElse("")}" , "gender" : "${gender.getOrElse("")}" , "dob" : "${dob.getOrElse("")}" }"""
  }
}
