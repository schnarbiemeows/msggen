/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

/**
  * class of SimpleAddressRecord
  */
case class SimpleAddressRecord(val accountId: String, val address: SimpleAddress) {

  /**
    * method to convert SimpleAddressRecord to CSV format
    * @return
    */
  def toCSV():String = {
    s"${accountId},${address.addressLine1.getOrElse("")},${address.addressLine2.getOrElse("")}," +
      s"${address.city.getOrElse("")},${address.stateCode.getOrElse("")},${address.zip5.getOrElse("")}"
  }

  def toJSON():String = {
    s"""{ "accountId" : "${accountId}" , "addressLine1" : "${address.addressLine1.getOrElse("")}" , "addressLine2" : "${address.addressLine2.getOrElse("")}" , "city" : "${address.city.getOrElse("")}" , "stateCode" : "${address.stateCode.getOrElse("")}" , "zip5" : "${address.zip5.getOrElse("")}" }"""
  }
}
