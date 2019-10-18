/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors

import java.util.Properties

import akka.actor.Actor
import com.scala.classes.actors.messages.{KafkaProducerFinishedMessage, KafkaProducerMessage}
import com.scala.classes.kafka.SimpleKafkaProducer
import com.scala.classes.utilities.{Configuration, FileIO, LogUtil, PropertyLoader}

/**
  * this class initializes a SimpleKafkaProducer class. It then waits for filenames
  * from the RecordsMakerControllerActor. When it receives a filename, it reads
  * in the file, and then pushes each line in the file to the SimpleKafkaProducer
  * @param properties - singleton Properties object
  */
class KafkaProducerActor(val properties: Properties) extends Actor{

  val numFiles:Int = properties.getProperty(Configuration.MODE4_NUM_FILES).toString.toInt
  var fileNum:Int = 0
  val producer:SimpleKafkaProducer = new SimpleKafkaProducer(properties)

  /**
    * actor's main method
    * @return - KafkaProducerFinishedMessage
    */
  override def receive: Receive = {
    case KafkaProducerMessage(filename) => {
      LogUtil.msggenMasterLoggerDEBUG(s"inside KafkaProducerActor.receive method")
      fileNum+=1
      val adjustedFileName = PropertyLoader.revAdjustConfigFilePath(filename)
      LogUtil.msggenMasterLoggerDEBUG(s"reading in file : ${adjustedFileName}")
      val records:List[String] = FileIO.simpleReadInFile(adjustedFileName).toList
      LogUtil.msggenMasterLoggerDEBUG(s"pushing records to KafkaProducer")
      for(item <- records) {
        producer.pushMessageToTopic(item)
        Thread.sleep(1000)
      }
      LogUtil.msggenMasterLoggerDEBUG(s"finished pushing records to KafkaProducer")
      if(fileNum == numFiles) {
        LogUtil.msggenMasterLoggerDEBUG(s"closing the KafkaProducer")
        producer.closeProducer()
      } else {
        LogUtil.msggenMasterLoggerDEBUG(s"${fileNum} not equal to ${numFiles}")
      }
      sender ! KafkaProducerFinishedMessage
    }
  }
}