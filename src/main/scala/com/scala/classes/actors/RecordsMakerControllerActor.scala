/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors

import java.util.Properties
import java.util.concurrent.ArrayBlockingQueue

import akka.actor.{Actor, ActorRef, Props}
import com.scala.classes.actors.messages._
import com.scala.classes.posos.{Record, RecordsTemplate}
import com.scala.classes.utilities.{Configuration, DateUtils, LogUtil, StringUtils}

/**
  *
  */
class RecordsMakerControllerActor(val template: RecordsTemplate, val properties: Properties,
                                  val finishedQueue:ArrayBlockingQueue[String])
  extends Actor {

  /**
    * N = numThreads
    */
  val numThreads:Int = properties.get(Configuration.MODE4_NUM_THREADS).toString.toInt
  LogUtil.msggenMasterLoggerDEBUG("")
  LogUtil.msggenMasterLoggerDEBUG(s"numTheads = ${numThreads}")
  /**
    * F = numFiles
    */
  val numFiles:Int = properties.get(Configuration.MODE4_NUM_FILES).toString.toInt
  LogUtil.msggenMasterLoggerDEBUG(s"numFiles = ${numFiles}")
  /**
    * R = numRecordsPerFile
    */
  val numRecordsPerFile:Int = properties.get(Configuration.MODE4_NUM_RECORDS).toString.toInt
  LogUtil.msggenMasterLoggerDEBUG(s"numRecordsPerFile = ${numRecordsPerFile}")
  LogUtil.msggenMasterLoggerDEBUG("")
  var recordArray1:Array[Record] = new Array[Record](numRecordsPerFile)
  var recordArray2 = Array.ofDim[Record](numFiles, numRecordsPerFile)
  /**
    * actors that will be used for making the records and transferring them from
    */
  var actors:Array[ActorRef] = new Array[ActorRef](numThreads)
  for(i <- 0 until actors.length) {
    //LogUtil.msggenMasterLoggerDEBUG("creating a new RecordMakerAndCopierActor")
    actors(i) = context.actorOf(Props(new RecordMakerAndCopierActor(template,recordArray1,
      recordArray2,i)))
  }
  var fileWriter: ActorRef = context.actorOf(Props(new FileWriterActor(recordArray2,properties)))

  var fileArrayIndex:Int = 0
  var makeRecordCount:Int = 0
  var copyRecordCount:Int = 0
  var recordMadeCount:Int = 0
  var recordCopiedCount:Int = 0
  var filesFinishedCount:Int = 0
  var inRecordMakingPhase:Boolean = false
  var inRecordCopyingPhase:Boolean = false
  var inFileMakingPhase:Boolean = false

  override def receive: Receive = {
    case StartMessage => initiateRecordMaking()
    case FinishedMakingRecordMessage(threadNum) => incrementCheckAndCount(threadNum)
    case FinishedCopyingRecordMessage(threadNum) => incrementCopyAndCount(threadNum)
    case FinishedWritingFileMessage => {
      filesFinishedCount+=1
      LogUtil.msggenMasterLoggerDEBUG(s"files Finished Count = ${filesFinishedCount} , numFiles = ${numFiles}")
      if(filesFinishedCount==numFiles) {
        LogUtil.msggenMasterLoggerDEBUG("pushing DONE into queue")
        finishedQueue.put("DONE")
      }
    }
  }

  def initiateRecordMaking():Unit = {
    LogUtil.msggenMasterLoggerDEBUG("")
    LogUtil.msggenMasterLoggerDEBUG("inside initiateRecordMaking")
    for(i <- 0 until actors.length) {
      actors(i) ! MakeRecordMessage(makeRecordCount)
      makeRecordCount+=1
    }
    inRecordMakingPhase = true
    LogUtil.msggenMasterLoggerDEBUG(s"made ${makeRecordCount} records")
    LogUtil.msggenMasterLoggerDEBUG("")
  }

  def initiateRecordCopying():Unit = {
    LogUtil.msggenMasterLoggerDEBUG("")
    LogUtil.msggenMasterLoggerDEBUG("inside initiateRecordCopying")
    for(i <- 0 until actors.length) {
      actors(i) ! CopyRecordMessage(copyRecordCount,fileArrayIndex)
      copyRecordCount+=1
    }
    inRecordCopyingPhase = true
    LogUtil.msggenMasterLoggerDEBUG(s"copied ${copyRecordCount} records")
    LogUtil.msggenMasterLoggerDEBUG("")
  }

  def initiateFileCreation():Unit = {
    LogUtil.msggenMasterLoggerDEBUG("")
    LogUtil.msggenMasterLoggerDEBUG("inside initiateFileCreation")
    val currentDateTime = properties.get(Configuration.MODE4_OUTPUT_FILE).toString +
      properties.get(Configuration.MODE4_FILE_PREPENDER).toString + "_" + StringUtils.removeColons(DateUtils.getStringFromTime(DateUtils.nowTime()))
    LogUtil.msggenMasterLoggerDEBUG(s"calling WriteToFileMessage(${currentDateTime})")
    fileWriter ! WriteToFileMessage(currentDateTime,fileArrayIndex)
    fileArrayIndex+=1
    inFileMakingPhase = true
    LogUtil.msggenMasterLoggerDEBUG("")
  }

  def incrementCheckAndCount(number:Int):Unit = {
    recordMadeCount+=1
    LogUtil.msggenMasterLoggerDEBUG(s"inside incrementCheckAndCount, records made = ${recordMadeCount}")
    if(makeRecordCount<numRecordsPerFile) {
      LogUtil.msggenMasterLoggerDEBUG(s"sending another MakeRecordMessage(${makeRecordCount})")
      actors(number) ! MakeRecordMessage(makeRecordCount)
      makeRecordCount+=1
    } else {
      if (!inRecordCopyingPhase) {
        LogUtil.msggenMasterLoggerDEBUG("initiating record copying")
        initiateRecordCopying()
      }
      if (recordMadeCount == numRecordsPerFile) {
        LogUtil.msggenMasterLoggerDEBUG("turning off record making phase")
        inRecordMakingPhase = false
        recordMadeCount = 0
        makeRecordCount = 0
      } else {
        LogUtil.msggenMasterLoggerDEBUG(s"not doing anything with this make record message")
      }
    }
  }

  def incrementCopyAndCount(number:Int):Unit = {
    recordCopiedCount+=1
    LogUtil.msggenMasterLoggerDEBUG(s"inside incrementCopyAndCount, records copied = ${recordCopiedCount}")
    if(copyRecordCount<numRecordsPerFile) {
      LogUtil.msggenMasterLoggerDEBUG(s"sending another CopyRecordMessage(${copyRecordCount},${fileArrayIndex})")
      actors(number) ! CopyRecordMessage(copyRecordCount,fileArrayIndex)
      copyRecordCount+=1
    } else {
      if(!inFileMakingPhase) {
        LogUtil.msggenMasterLoggerDEBUG(s"initiating file creation for file #${fileArrayIndex}")
        initiateFileCreation()
        if(fileArrayIndex<numFiles) {
          LogUtil.msggenMasterLoggerDEBUG("and starting a new batch of records")
          initiateRecordMaking()
        }
      }
      if(recordCopiedCount==numRecordsPerFile) {
        LogUtil.msggenMasterLoggerDEBUG("turning off record copying phase")
        inFileMakingPhase = false
        inRecordCopyingPhase = false
        recordCopiedCount = 0
        copyRecordCount=0
      } else {
        LogUtil.msggenMasterLoggerDEBUG(s"not doing anything with this copy record message")
      }
    }
  }
}
