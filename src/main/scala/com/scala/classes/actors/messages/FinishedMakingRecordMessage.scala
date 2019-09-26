/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors.messages

/**
  * this is an message object that is sent from the RecordsMakerAndCopierActor
  * actors to the RecordsMakerControllerActor actor. It is a notification that
  * a record has been successfully created
  * @param threadNum - actor number of the RecordsMakerAndCopierActor actor
  */
case class FinishedMakingRecordMessage(threadNum: Int) {

}
