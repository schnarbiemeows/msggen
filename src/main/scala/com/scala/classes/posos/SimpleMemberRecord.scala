/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos


/**
  * class of SimpleMemberRecord
  */
class SimpleMemberRecord {

  // TODO - remove null references
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

  /**
    * this method will return the record as a csv string
    * @return - csv string
    */
  def toCSV():String = {
    s"${accountId},${subscriberId},${alphaPrefix},${dependentNumber},${socialSecurityNumber},${firstName},${middleName},${lastName},${gender},${dob}"
  }

}
