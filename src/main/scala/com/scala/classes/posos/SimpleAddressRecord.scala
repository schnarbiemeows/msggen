/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

/**
  * class of SimpleAddressRecord
  */
class SimpleAddressRecord {

  var accountId: String = null
  var address: SimpleAddress = null

  /**
    * method to convert SimpleAddressRecord to CSV format
    * @return
    */
  def toCSV():String = {
    s"${accountId},${address.addressLine1},${address.addressLine2},${address.city},${address.stateCode},${address.zip5}"
  }
}
