/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors

import akka.actor.Actor
import com.scala.classes.actors.messages.{FinishedWritingFileMessage, WriteToFileMessage}
import com.scala.classes.posos.Record
import com.scala.classes.utilities.{Configuration, FileIO, LogUtil, PropertyLoader}

/**
  * this is an actor class. Its purpose is to pull records from the inner array of a
  * double array of records, and then call a FileIO method for writing these records
  * out to either a CSV or a JSON file
  * @param recordArray2 - the double array that stores the records
  */
class FileWriterActor(recordArray2:Array[Array[Record]]) extends Actor{
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
        val fileType = PropertyLoader.getProperty(Configuration.MODE4_OUTPUT_FILE_TYPE).toString.toLowerCase
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
    FileIO.writeGenericRecordToFile(recordArray2(index),fileName)
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
    val mode:Int = PropertyLoader.getProperty(Configuration.MODE).toString.toInt
    if(mode == 7) {
      val primaryKeys:Array[String] = FileIO.simpleReadInFile(PropertyLoader.getProperty(Configuration.MODE1_OUTPUT_FILE))
      for((row,i) <- primaryKeys.zipWithIndex) {
        recordArray2(0)(i).fieldValues(0) = row
      }
    }
  }
}
