/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import java.util.Properties

import com.scala.classes.locks.PrimaryKeyLock
import com.scala.classes.processes.PrimaryKeyMakerThread
import com.scala.classes.utilities.{Configuration, DateUtils, FileIO, LogUtil}
import com.scala.classes.validators.PrimaryKeyMakerValidator

/**
  * class that is used to make a primary key
  * @param mode - the mode of the program
  * @param properties - singleton Properties object
  */
class PrimaryKeyMakerMode(val mode: Int, val properties: Properties) extends Mode {
  /**
    * main run method
    */
  override def run(): Unit = {
    var runStart = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("inside PrimaryKeyMakerMode main method");
    if(PrimaryKeyMakerValidator.validate(properties)) {
      LogUtil.msggenMasterLoggerDEBUG("configuration for the primary key maker mode validated");
      val numberOfPrimaryKeys:Int = properties.get(Configuration.MODE1_NUM_PRIMARY_KEYS_TO_MAKE).toString.toInt
      LogUtil.msggenMasterLoggerDEBUG(s"number of unique primary keys to make = ${numberOfPrimaryKeys}");
      val characters:String = properties.get(Configuration.MODE1_CHARACTERS).toString
      val pkLength:Int = properties.get(Configuration.MODE1_PRIMARY_LENGTH).toString.toInt
      val outputfile:String = properties.get(Configuration.MODE1_OUTPUT_FILE).toString
      LogUtil.msggenMasterLoggerDEBUG(s"output file location for the primary keys = ${outputfile}");
      val primaryKeys:scala.collection.mutable.Set[String] = makeRandomPrimaryKeys(numberOfPrimaryKeys,characters,pkLength)
      val primaryKeyList:List[String] = primaryKeys.toList
      LogUtil.msggenMasterLoggerDEBUG("primary keys made");
      FileIO.outputAnyListToFile(primaryKeyList,outputfile)
      LogUtil.msggenMasterLoggerDEBUG("primary key making process complete");
    } else {
      LogUtil.msggenMasterLoggerDEBUG("configuration for the primary key maker mode was not validated!");
    }
    val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
    LogUtil.logTime(s"PrimaryKeyMakerMode run() method time = ${runEnd._1} milliseconds")
  }

  def makeRandomPrimaryKeys(numberOfPrimaryKeys: Int, characters:String, pkLength:Int): scala.collection.mutable.Set[String] = {
    LogUtil.msggenThread2LoggerDEBUG("making random Primary Keys")
    // locks
    val lock:PrimaryKeyLock = new PrimaryKeyLock()
    // set to store primary keys in
    val primaryKeySet:scala.collection.mutable.Set[String] = scala.collection.mutable.Set[String]()
    // make 4 runnables
    val run1:PrimaryKeyMakerThread = new PrimaryKeyMakerThread(lock,primaryKeySet,0,numberOfPrimaryKeys/4,characters,pkLength)
    val run2:PrimaryKeyMakerThread = new PrimaryKeyMakerThread(lock,primaryKeySet,1,numberOfPrimaryKeys/4,characters,pkLength)
    val run3:PrimaryKeyMakerThread = new PrimaryKeyMakerThread(lock,primaryKeySet,2,numberOfPrimaryKeys/4,characters,pkLength)
    val run4:PrimaryKeyMakerThread = new PrimaryKeyMakerThread(lock,primaryKeySet,3,numberOfPrimaryKeys/4,characters,pkLength)
    // make 4 threads
    val thr1:Thread = new Thread(run1)
    val thr2:Thread = new Thread(run2)
    val thr3:Thread = new Thread(run3)
    val thr4:Thread = new Thread(run4)
    // start 4 threads
    thr1.start()
    thr2.start()
    thr3.start()
    thr4.start()
    try {
      // join 4 threads
      thr1.join()
      thr2.join()
      thr3.join()
      thr4.join()
    } catch {
      case e:InterruptedException  => e.printStackTrace()
    }
    LogUtil.msggenThread2LoggerDEBUG(s"Number of generated primary keys = ${primaryKeySet.size}");
    primaryKeySet
  }
}
