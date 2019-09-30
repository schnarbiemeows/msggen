/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors.controllers

import java.time.LocalTime
import java.util.Properties
import java.util.concurrent.ArrayBlockingQueue

import akka.actor.{ActorSystem, Props}
import com.scala.classes.actors.RecordsMakerControllerActor
import com.scala.classes.actors.messages.StartMessage
import com.scala.classes.posos.RecordsTemplate
import com.scala.classes.utilities.{DateUtils, LogUtil}

/**
  * this class is used as a primary to initiate the actor system that will generate records
  * @param template - Record template
  * @param properties - singleton Properties object
  */
class RecordMakerController(val template: RecordsTemplate, val properties: Properties) {

  /**
    * main method to invoke the actors
    * @return - success indicator
    */
  def generateRecords(): Boolean = {
    var runStart = DateUtils.nowTime()
    var runStartLocal = DateUtils.nowTime()
    var runEndLocal:Tuple2[Long, LocalTime] = (0,runStartLocal)
    LogUtil.msggenMasterLoggerDEBUG("inside RecordMakerController.generateRecords main method");
    var successfull = true
    var blockingQueue  = new ArrayBlockingQueue[String](1)
    LogUtil.msggenMasterLoggerDEBUG("initializing the actor system")
    val system = ActorSystem("RecordMakingActorSystem")
    LogUtil.msggenMasterLoggerDEBUG("creating the primary record making controller")
    val recordsMakerControllerActor = system.actorOf(Props(new RecordsMakerControllerActor(template,
      properties,blockingQueue)), name = "recordsMakerControllerActor")
    runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
    LogUtil.logTime(s"creating the actor system took => ${runEndLocal._1} milliseconds")
    runStartLocal = runEndLocal._2
    LogUtil.msggenMasterLoggerDEBUG("sending a StartMessage to the primary record making controller")
    recordsMakerControllerActor ! StartMessage
    runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
    LogUtil.logTime(s"sending the message to initiate the actor system took => ${runEndLocal._1} milliseconds")
    runStartLocal = runEndLocal._2
    LogUtil.msggenMasterLoggerDEBUG("polling the ArrayBlockingQueue")
    blockingQueue.take()
    runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
    //LogUtil.logTime(s"sending the message to initiate the actor system took => ${runEndLocal._1} milliseconds")
    runStartLocal = runEndLocal._2
    LogUtil.msggenMasterLoggerDEBUG("shutting down the actor system")
    system.terminate()
    runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
    LogUtil.logTime(s"terminating the actor system took => ${runEndLocal._1} milliseconds")
    runStartLocal = runEndLocal._2
    val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
    LogUtil.logTime(s"RecordMakerController.generateRecords run() method time = ${runEnd._1} milliseconds")
    successfull
  }
}
