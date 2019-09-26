/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

import org.apache.logging.log4j._

/**
  * utility class for generating logs
  */
object LogUtil {

  val msgenmasterLogger:Logger = LogManager.getLogger("msgGenMasterLog")
  val msggenThread1Logger:Logger = LogManager.getLogger("msgGenThread1Log");
  val msggenThread2Logger:Logger = LogManager.getLogger("msgGenThread2Log");
  val msggenThread3Logger:Logger = LogManager.getLogger("msgGenThread3Log");
  val msggenThread4Logger:Logger = LogManager.getLogger("msgGenThread4Log");
  val msggenTimerLogger:Logger = LogManager.getLogger("msgGenTimingLog");
  val msggenValidatorLog:Logger = LogManager.getLogger("msgGenValidatorLog")
  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenMasterLoggerDEBUG(message: String): Unit = {
    this.msgenmasterLogger.debug(message)
    println(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenMasterLoggerWARN(message: String): Unit = {
    this.msgenmasterLogger.warn(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenMasterLoggerERROR(message: String): Unit = {
    this.msgenmasterLogger.error(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenThread1LoggerDEBUG(message: String): Unit = {
    this.msggenThread1Logger.debug(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenThread1LoggerWARN(message: String): Unit = {
    this.msggenThread1Logger.warn(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenThread1LoggerERROR(message: String): Unit = {
    this.msggenThread1Logger.error(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenThread2LoggerDEBUG(message: String): Unit = {
    this.msggenThread2Logger.debug(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenThread2LoggerWARN(message: String): Unit = {
    this.msggenThread2Logger.warn(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenThread2LoggerERROR(message: String): Unit = {
    this.msggenThread2Logger.error(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenThread3LoggerDEBUG(message: String): Unit = {
    this.msggenThread1Logger.debug(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenThread3LoggerWARN(message: String): Unit = {
    this.msggenThread1Logger.warn(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenThread3LoggerERROR(message: String): Unit = {
    this.msggenThread1Logger.error(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenThread4LoggerDEBUG(message: String): Unit = {
    this.msggenThread2Logger.debug(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenThread4LoggerWARN(message: String): Unit = {
    this.msggenThread2Logger.warn(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def msggenThread4LoggerERROR(message: String): Unit = {
    this.msggenThread2Logger.error(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def logTime(message: String): Unit = {
    this.msggenTimerLogger.debug(message)
    println(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def logTimeWARN(message: String): Unit = {
    this.msggenTimerLogger.warn(message)
  }

  /**
    * method for message logging
    * @param message - the message to log
    */
  def logTimeERROR(message: String): Unit = {
    this.msggenTimerLogger.error(message)
  }

  /**
    * logger for validations
    * @param message - the message to log
    */
  def logValidationMessage(message: String): Unit = {
    this.msggenValidatorLog.debug(message)
  }

  /**
    * logger for validations
    * @param message - the message to log
    */
  def logValidationWARN(message: String): Unit = {
    this.msggenValidatorLog.warn(message)
  }

  /**
    * logger for validations
    * @param message - the message to log
    */
  def logValidationERROR(message: String): Unit = {
    this.msggenValidatorLog.error(message)
  }

  /**
    * method to chose a log file based on the thread number of the calling process or class
    * @param message = message to log
    * @param num = thread number(0 for non-thread classes)
    */
  def logByNum(message: String, num: Int): Unit = {
    num match {
      case 0 => this.msggenThread1Logger.debug(message)
      case 1 => this.msggenThread2Logger.debug(message)
      case 2 => this.msggenThread3Logger.debug(message)
      case 3 => this.msggenThread4Logger.debug(message)
      case _ => this.msggenMasterLoggerDEBUG(message)
    }
  }
}
