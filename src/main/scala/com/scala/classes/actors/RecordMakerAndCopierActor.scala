/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors

import akka.actor.Actor
import com.scala.classes.actors.messages.{CopyRecordMessage, FinishedCopyingRecordMessage, FinishedMakingRecordMessage, MakeRecordMessage}
import com.scala.classes.business.MakeGenericRecord
import com.scala.classes.posos.{Record, RecordsTemplate}

class RecordMakerAndCopierActor(val records: RecordsTemplate,
                                var firstRecordSet: Array[Record],
                                var secondRecordSet: Array[Array[Record]],
                                val threadNum: Int) extends Actor {

  override def receive: Receive = {
    case MakeRecordMessage(message)  => {
      firstRecordSet(message) = MakeGenericRecord.makeRecord(records)
      sender ! FinishedMakingRecordMessage(threadNum)
    }
    case CopyRecordMessage(message,i)  => {
      //LogUtil.msggenMasterLoggerDEBUG(s"i = ${i} , message = ${message}")
      secondRecordSet(i)(message) = firstRecordSet(message)
      //LogUtil.msggenMasterLoggerDEBUG(s"secondRecordSet(i)(message) = ${secondRecordSet(i)(message)}")
      sender ! FinishedCopyingRecordMessage(threadNum)
    }
  }
}
