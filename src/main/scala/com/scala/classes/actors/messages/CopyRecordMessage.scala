/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors.messages

/**
  * this is an message object that is sent from the RecordsMakerControllerActor
  * to the RecordsMakerAndCopierActor actors. It is a request to copy a record
  * from one array to another
  * @param number - inner array index of the record to copy to
  * @param arrayIndex - outer array index
  */
case class CopyRecordMessage(number:Int,arrayIndex:Int) {

}
