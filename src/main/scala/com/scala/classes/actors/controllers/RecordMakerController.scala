/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors.controllers

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
    LogUtil.msggenMasterLoggerDEBUG("inside RecordMakerController.generateRecords main method");
    var successfull = false

    var blockingQueue  = new ArrayBlockingQueue[String](1)
    LogUtil.msggenMasterLoggerDEBUG("initializing the actor system")
    val system = ActorSystem("RecordMakingActorSystem")
    LogUtil.msggenMasterLoggerDEBUG("creating the primary record making controller")
    val recordsMakerControllerActor = system.actorOf(Props(new RecordsMakerControllerActor(template,
      properties,blockingQueue)), name = "recordsMakerControllerActor")
    LogUtil.msggenMasterLoggerDEBUG("sending a StartMessage to the primary record making controller")
    recordsMakerControllerActor ! StartMessage
    LogUtil.msggenMasterLoggerDEBUG("polling the ArrayBlockingQueue")
    blockingQueue.take()
    LogUtil.msggenMasterLoggerDEBUG("shutting down the actor system")
    system.terminate()
    val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
    LogUtil.logTime(s"RecordMakerController.generateRecords run() method time = ${runEnd._1} milliseconds")
    successfull
  }
}
