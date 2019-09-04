/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

/**
  * class of a MemberAddressRecord
  */
class MemberAddressRecord {

  var firstname: String = null
  var lastname: String = null
  var middlename: String = null
  var address1: Address = null
  var address2: Address = null
  var address3: Address = null
  var ssn: String = null
  var subscriberRelationshiplookupcode: String = null
  var subscriberRelationshipvalue: String = null
  var dob: String = null
  var email: String = null
  var identifiers: Array[Identifier] = Array[Identifier]()
  var genderlookupcode: String = null
  var gendervalue: String = null
  var phone:PhoneNumber = null
  var maritalstatuslookupcode: String = null
  var maritalstatusvalue: String = null
  var phisecuritycodelookupcode: String = null
  var phisecuritycodevalue: String = null
  var uri: String = null
  var sources: Array[Source] = Array[Source]()
  var createdtime: String = null
  var updatedtime: String = null
  var loaddt: String = null

}
