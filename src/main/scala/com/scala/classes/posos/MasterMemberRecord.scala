/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

/**
  * class of a record in the Master Member Hive table
  */
class MasterMemberRecord {

  var attributes: Option[Attributes] = None
  var createdTime:Option[String] = None
  var updatedTime:Option[String] = None
  var sources: Array[Source] = Array[Source]()
  var uri:Option[String] = None

}
