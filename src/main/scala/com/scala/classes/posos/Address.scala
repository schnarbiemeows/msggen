/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

/**
  * class of Address
  */
class Address {

  var addressLine1: Option[String] = None
  var addressLine2: Option[String] = None
  var addressType: Option[AddressType] = None
  var city: Option[String] = None
  var country: Option[Country] = None
  var stateProvince: Option[StateProvince] = None
  var zip4: Option[String] = None
  var zip5: Option[String] = None

}
