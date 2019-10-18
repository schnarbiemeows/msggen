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
  * this is an actor class. This class is the parent controller actor for a process
  * that makes,copies, and then creates text file of data
  */
class RecordsMakerControllerActor(val template: RecordsTemplate, val properties: Properties,
                                  val finishedQueue:ArrayBlockingQueue[String])
  extends Actor {

  // TODO - do we want more timing logs in here?
  /**
    * N = numThreads to use for making/copying Record data
    */
  val numThreads:Int = properties.get(Configuration.MODE4_NUM_THREADS).toString.toInt
  LogUtil.msggenMasterLoggerDEBUG("")
  LogUtil.msggenMasterLoggerDEBUG(s"numTheads = ${numThreads}")
  /**
    * F = numFiles - number of output csv or json files to make with Record data
    */
  val numFiles:Int = properties.get(Configuration.MODE4_NUM_FILES).toString.toInt
  LogUtil.msggenMasterLoggerDEBUG(s"numFiles = ${numFiles}")
  /**
    * R = numRecordsPerFile - number of Record records in each output file
    */
  val numRecordsPerFile:Int = properties.get(Configuration.MODE4_NUM_RECORDS).toString.toInt
  LogUtil.msggenMasterLoggerDEBUG(s"numRecordsPerFile = ${numRecordsPerFile}")
  LogUtil.msggenMasterLoggerDEBUG("")
  /**
    * primary array for storing an intial batch of created Record objects
    */
  var recordArray1:Array[Record] = new Array[Record](numRecordsPerFile)
  /**
    * second, double array for storing Record objects that will be written to
    * output files, with dimensions of F x R
    */
  var recordArray2 = Array.ofDim[Record](numFiles, numRecordsPerFile)
  /**
    * actors that will be used for making the records and transferring them from
    * recordArray1 to recordArray2
    */
  var actors:Array[ActorRef] = new Array[ActorRef](numThreads)
  for(i <- 0 until actors.length) {
    //LogUtil.msggenMasterLoggerDEBUG("creating a new RecordMakerAndCopierActor")
    actors(i) = context.actorOf(Props(new RecordMakerAndCopierActor(template,recordArray1,
      recordArray2,i)))
  }
  /**
    * actor that will be used to pull records from recordArray2 and write them out to a file
    */
  var fileWriter: ActorRef = context.actorOf(Props(new FileWriterActor(recordArray2,properties)))
  /**
    * actor that will be used to push records to a kafka topic
    */
  val kafkActor:ActorRef = context.actorOf(Props(new KafkaProducerActor(properties)))
  /**
    * indexes and toggles used to keep track of how many records have been requested to
    * be made or copied, how many records have successfully been made or copied, how many
    * files havebeen requested to be created, and how many have been created.
    *
    * there are 3 phases to the process:
    * 1. record making phase
    * 2. record copying phase
    * 3. file creation(csv or json)
    *
    * each phases will occur F times, because each file will need records made for it,
    * copied for it, and then finally written out to file
    * phase 3 for a file can run concurrently with phases 1 and 2 for the next file,
    * but in general, phases 1 and 2 for a given file
    * should happen in series
    */
  var fileArrayIndex:Int = 0
  var makeRecordCount:Int = 0
  var copyRecordCount:Int = 0
  var recordMadeCount:Int = 0
  var recordCopiedCount:Int = 0
  var filesFinishedCount:Int = 0
  var inRecordMakingPhase:Boolean = false
  var inRecordCopyingPhase:Boolean = false
  var inFileMakingPhase:Boolean = false

  /**
    * main actor method
    * @return - Nothing
    */
  override def receive: Receive = {
    case StartMessage => initiateRecordMaking()
    case FinishedMakingRecordMessage(threadNum) => incrementCheckAndCount(threadNum)
    case FinishedCopyingRecordMessage(threadNum) => incrementCopyAndCount(threadNum)
    case FinishedWritingFileMessage(filename) => {
      filesFinishedCount+=1
      LogUtil.msggenMasterLoggerDEBUG(s"files Finished Count = ${filesFinishedCount} , numFiles = ${numFiles}")
      val mode = properties.getProperty(Configuration.MODE).toString.toInt
      if(mode == 8) {
        kafkActor ! KafkaProducerMessage(filename)
      } else {
        if(filesFinishedCount==numFiles) {
          LogUtil.msggenMasterLoggerDEBUG("pushing DONE into queue")
          finishedQueue.put("DONE")
        }
      }
    }
    case KafkaProducerFinishedMessage => {
      if(filesFinishedCount==numFiles) {
        LogUtil.msggenMasterLoggerDEBUG("pushing DONE into queue")
        finishedQueue.put("DONE")
      }
    }
  }

  /**
    * this method is used to initiate a record making phase. We need to initiate
    * the phase by sending N requests to each of the N actors we are using
    * then we need to toggle the inRecordMakingPhase on to indicate that we are
    * in a record making phase
    */
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

  /**
    * this method is used to initiate a record copying phase. We need to initiate
    * the phase by sending N requests to each of the N actors we are using
    * then we need to toggle the inRecordCopyingPhase on to indicate that we are
    * in a record copying phase
    */
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

  /**
    * method that is used to initiate a new request to make an output file
    * this request is sent to a FileWriterActor using a WriteToFileMessage
    * before that, the method generates the name of the file to be made
    * after the message is sent, the inFileMakingPhase is toggled to indicate that
    * we are in a file creation phase
    */
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

  /**
    * method that gets invoked each time a FinishedMakingRecordMessage message comes back
    * from a RecordMakerAndCopierActor
    * @param number - the actor number of the sender
    */
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

  /**
    * method that gets invoked each time a FinishedCopyingRecordMessage message comes back
    * from a RecordMakerAndCopierActor
    * @param number - the actor number of the sender
    */
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
