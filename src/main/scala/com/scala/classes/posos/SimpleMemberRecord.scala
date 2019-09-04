/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

/**
  * class of SimpleMemberRecord
  */
class SimpleMemberRecord {

  var accountId: String = null
  var socialSecurityNumber: String = null
  var subscriberId: String = null
  var dependentNumber: String = null
  var alphaPrefix: String = null
  var gender: String = null
  var dob: String = null
  var firstName: String = null
  var middleName: String = null
  var lastName: String = null

  def toCSV():String = {
    s"${accountId},${subscriberId},${alphaPrefix},${dependentNumber},${socialSecurityNumber},${firstName},${middleName},${lastName},${gender},${dob}"
  }


}
