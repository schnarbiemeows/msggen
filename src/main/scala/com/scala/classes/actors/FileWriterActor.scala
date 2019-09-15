/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors

import java.util.Properties

import akka.actor.Actor
import com.scala.classes.actors.messages.{FinishedWritingFileMessage, WriteToFileMessage}
import com.scala.classes.posos.Record
import com.scala.classes.utilities.{FileIO, LogUtil}

class FileWriterActor(recordArray2:Array[Array[Record]],properties:Properties) extends Actor{
  override def receive: Receive = {
    case WriteToFileMessage(fileName,i) => {
      LogUtil.msggenMasterLoggerDEBUG(s"inside FileWriterActor.writeToFile method, writing file for Array index = ${i}")
      var successfull:Boolean = writeToFile(fileName,i)
      if(successfull) {
        LogUtil.msggenMasterLoggerDEBUG("successfully created our file")
        sender ! FinishedWritingFileMessage
      }
    }
  }

  def writeToFile(fileName:String,index:Int):Boolean = {
    var successfull:Boolean = true
    //LogUtil.msggenMasterLoggerDEBUG(s"i = ${index} , recordArray2(i)(0) = ${recordArray2(index)(0)}")
    FileIO.writeGenericRecordToFile(recordArray2(index),fileName,properties)
    successfull
  }
}
