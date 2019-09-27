/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business
import java.util.Properties

import com.scala.classes.locks.SSNlock
import com.scala.classes.processes.SSNMakerThread
import com.scala.classes.utilities.{Configuration, FileIO, LogUtil}

import scala.collection.JavaConversions._


/**
  * Class for making a file of Social Security Numbers
  * @param mode - run mode of the program
  * @param properties - singleton Properties object
  */
class SSNMakerMode(val mode: Int, val properties: Properties) extends Mode {

  /**
    * main run method
    */
  override def run(): Unit = {
    LogUtil.msggenThread2LoggerDEBUG("inside SSNMakerMode");
    for (key <- properties.keySet) {
      val keyStr:String = key.asInstanceOf[String]
      LogUtil.msggenThread1LoggerDEBUG(keyStr + " = " + properties.getProperty(keyStr))
    }
    var numToMake: Int = properties.get(Configuration.MODE0_SSN_NUMBER_TO_MAKE).toString.toInt
    var ssns:scala.collection.mutable.Set[Int] = makeRandomSSNs(numToMake)
    val filepath:String = properties.get(Configuration.MODE0_SSN_OUTPUT_FILE).toString
    val ssnList:List[Int] = ssns.toList
    FileIO.outputToFile(ssnList,filepath)
  }

  /**
    * method to make a bunch of random Social Security Numbers
    * @param numberOfSsns = number of SS #'s to make
    * @return - ssnSet
    */
  def makeRandomSSNs(numberOfSsns: Int): scala.collection.mutable.Set[Int] = {
    LogUtil.msggenThread2LoggerDEBUG("making random SSN")
    // locks
    val lock:SSNlock = new SSNlock()
    // set to store SS #'s in
    var ssnSet:scala.collection.mutable.Set[Int] = scala.collection.mutable.Set[Int]()
    // make 4 runnables
    var run1:SSNMakerThread = new SSNMakerThread(lock,ssnSet,0,numberOfSsns/4)
    var run2:SSNMakerThread = new SSNMakerThread(lock,ssnSet,1,numberOfSsns/4)
    var run3:SSNMakerThread = new SSNMakerThread(lock,ssnSet,2,numberOfSsns/4)
    var run4:SSNMakerThread = new SSNMakerThread(lock,ssnSet,3,numberOfSsns/4)
    // make 4 threads
    var thr1:Thread = new Thread(run1)
    var thr2:Thread = new Thread(run2)
    var thr3:Thread = new Thread(run3)
    var thr4:Thread = new Thread(run4)
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
    LogUtil.msggenThread2LoggerDEBUG("Number of generated Social Security Numbers = " + ssnSet.size);
    ssnSet
  }

}