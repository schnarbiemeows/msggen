/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors

import java.util.Properties

import akka.actor.Actor
import com.scala.classes.actors.messages.{FinishedWritingFileMessage, WriteToFileMessage}
import com.scala.classes.posos.Record
import com.scala.classes.utilities.{Configuration, FileIO, LogUtil}

/**
  * this is an actor class. Its purpose is to pull records from the inner array of a
  * double array of records, and then call a FileIO method for writing these records
  * out to either a CSV or a JSON file
  * @param recordArray2 - the double array that stores the records
  * @param properties - singleton Properties object
  */
class FileWriterActor(recordArray2:Array[Array[Record]],properties:Properties) extends Actor{
  /**
    * actor's main method
    * @return
    */
  override def receive: Receive = {
    case WriteToFileMessage(fileName,i) => {
      LogUtil.msggenMasterLoggerDEBUG(s"inside FileWriterActor.writeToFile method, writing file for Array index = ${i}")
      mode7insertPrimaryKey()
      val successfull:Boolean = writeToFile(fileName,i)
      if(successfull) {
        LogUtil.msggenMasterLoggerDEBUG("successfully created our file")
        val fileType = properties.get(Configuration.MODE4_OUTPUT_FILE_TYPE).toString.toLowerCase
        sender ! FinishedWritingFileMessage(fileName+"."+fileType)
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
    val successfull:Boolean = true
    FileIO.writeGenericRecordToFile(recordArray2(index),fileName,properties)
    successfull
  }

  /**
    * this method is only used in mode 7
    * it has a prerequisite that mode1 was run earlier to generate a file of
    * primary keys. the # of keys exactly equals the number of records that this
    * process is making. The overall process also has to be configured to only
    * create 1 file(mode4.numfiles)
    * also mode4.numberofrecordsperfile = mode1.numberofprimariestomake
    * It will read in this file of keys and replace the first field of each record
    * in our Record array with this key
    */
  def mode7insertPrimaryKey():Unit = {
    val mode:Int = properties.getProperty(Configuration.MODE).toString.toInt
    if(7==mode) {
      val primaryKeys:Array[String] = FileIO.simpleReadInFile(properties.getProperty(Configuration.MODE1_OUTPUT_FILE))
      for((row,i) <- primaryKeys.zipWithIndex) {
        recordArray2(0)(i).fieldValues(0) = row
      }
    }
  }
}
