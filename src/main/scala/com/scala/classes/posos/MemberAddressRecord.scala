/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

/**
  * class of a MemberAddressRecord
  */
class MemberAddressRecord {

  var firstname:Option[String] = None
  var lastname:Option[String] = None
  var middlename:Option[String] = None
  var address1: Option[Address] = None
  var address2: Option[Address] = None
  var address3: Option[Address] = None
  var ssn:Option[String] = None
  var subscriberRelationshiplookupcode:Option[String] = None
  var subscriberRelationshipvalue:Option[String] = None
  var dob:Option[String] = None
  var email:Option[String] = None
  var identifiers: Array[Identifier] = Array[Identifier]()
  var genderlookupcode:Option[String] = None
  var gendervalue:Option[String] = None
  var phone:Option[PhoneNumber] = None
  var maritalstatuslookupcode:Option[String] = None
  var maritalstatusvalue:Option[String] = None
  var phisecuritycodelookupcode:Option[String] = None
  var phisecuritycodevalue:Option[String] = None
  var uri:Option[String] = None
  var sources: Array[Source] = Array[Source]()
  var createdtime:Option[String] = None
  var updatedtime:Option[String] = None
  var loaddt:Option[String] = None

}
