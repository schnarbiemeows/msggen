/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors.messages

/**
  * this is an message object that is sent from the RecordsMakerControllerActor
  * to the FileWriterActor actors. It is a request to create a file with the
  * results of the contents of an array
  * @param filename - the name of the file to create
  * @param arrayIndex - the index in the outer array where the inner array of records
  *                     is stored
  */
case class WriteToFileMessage(filename:String,arrayIndex:Int) {

}
