/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors

import akka.actor.Actor
import com.scala.classes.actors.messages.{CopyRecordMessage, FinishedCopyingRecordMessage, FinishedMakingRecordMessage, MakeRecordMessage}
import com.scala.classes.business.MakeGenericRecord
import com.scala.classes.posos.{Record, RecordsTemplate}
import com.scala.classes.utilities.{DateUtils, LogUtil}

/**
  * this is an actor class. It's purpose is to:
  * 1. create Records using meta-data contained in a RecordsTemplate into
  *   an initial array
  * 2. copy a Record object from an intial array to a second, double array
  *   at a given index
  * @param records - template that contains the meta-data needed to make a Record
  * @param firstRecordSet - initial array for storing records
  * @param secondRecordSet - double array for storing records
  * @param threadNum - actor number
  */
class RecordMakerAndCopierActor(val records: RecordsTemplate,
                                var firstRecordSet: Array[Record],
                                var secondRecordSet: Array[Array[Record]],
                                val threadNum: Int) extends Actor {

  /**
    * actor's main method
    * @return
    */
  override def receive: Receive = {
    case MakeRecordMessage(message)  => {
      var runStartLocal = DateUtils.nowTime()
      firstRecordSet(message) = MakeGenericRecord.makeRecord(records)
      var runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
      LogUtil.logTime(s"making a record for thread ${threadNum} took => ${runEndLocal._1} milliseconds")
      sender ! FinishedMakingRecordMessage(threadNum)
    }
    case CopyRecordMessage(message,i)  => {
      var runStartLocal = DateUtils.nowTime()
      secondRecordSet(i)(message) = firstRecordSet(message)
      var runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
      LogUtil.logTime(s"copying a record for thread ${threadNum} took => ${runEndLocal._1} milliseconds")
      sender ! FinishedCopyingRecordMessage(threadNum)
    }
  }
}
