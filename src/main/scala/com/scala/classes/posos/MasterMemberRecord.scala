/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.posos

/**
  * Poso for a record in the Master Member Hive table
  */
class MasterMemberRecord {

  var attributes: Attributes = null
  var createdTime: String = null
  var updatedTime: String = null
  var sources: Array[Source] = Array[Source]()
  var uri: String = null

}
