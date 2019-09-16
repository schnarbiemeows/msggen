/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors

import java.util.Properties

import akka.actor.Actor
import com.scala.classes.actors.messages.{FinishedWritingFileMessage, WriteToFileMessage}
import com.scala.classes.posos.Record
import com.scala.classes.utilities.{FileIO, LogUtil}

/**
  * this is an actor class. Its purpose is to pull records from the inner array of a
  * double array of records, and then call a FileIO method for writing these records
  * out to either a CSV or a JSON file
  * @param recordArray2 - the double array that stores the records
  * @param properties - program's singleton Properties object
  */
class FileWriterActor(recordArray2:Array[Array[Record]],properties:Properties) extends Actor{
  /**
    * actor's main method
    * @return
    */
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

  /**
    * method to call the FileIO.writeGenericRecordToFile method
    * @param fileName - name of the file to create
    * @param index - inner index of the double array where the records are stored
    * @return - success indicator
    */
  def writeToFile(fileName:String,index:Int):Boolean = {
    var successfull:Boolean = true
    FileIO.writeGenericRecordToFile(recordArray2(index),fileName,properties)
    successfull
  }
}
