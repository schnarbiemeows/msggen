/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors.messages

/**
  * this is an message object that is sent from the FileWriterActor
  * actors to the RecordsMakerControllerActor actor. It is a notification that
  * a file has been successfully created, written to, and closed
  * @param filepath - full path to the file the actor just wrote to
  */
case class FinishedWritingFileMessage(filepath:String) {

}
