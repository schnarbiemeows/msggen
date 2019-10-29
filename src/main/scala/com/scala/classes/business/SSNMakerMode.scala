/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business
import com.scala.classes.locks.SSNlock
import com.scala.classes.processes.SSNMakerThread
import com.scala.classes.utilities.{Configuration, FileIO, LogUtil, PropertyLoader}


/**
  * Class for making a file of Social Security Numbers
  * @param mode - run mode of the program
  */
class SSNMakerMode(val mode: Int) extends Mode {

  /**
    * main run method
    */
  override def run(): Unit = {
    LogUtil.msggenThread2LoggerDEBUG("inside SSNMakerMode");
    val numToMake: Int = PropertyLoader.getProperty(Configuration.MODE0_SSN_NUMBER_TO_MAKE).toString.toInt
    val ssns:scala.collection.mutable.Set[String] = makeRandomSSNs(numToMake)
    val filepath:String = PropertyLoader.getProperty(Configuration.MODE0_SSN_OUTPUT_FILE).toString
    val ssnList:List[String] = ssns.toList
    FileIO.outputAnyListToFile(ssnList,filepath)
  }

  /**
    * method to make a bunch of random Social Security Numbers
    * @param numberOfSsns = number of SS #'s to make
    * @return - ssnSet
    */
  def makeRandomSSNs(numberOfSsns: Int): scala.collection.mutable.Set[String] = {
    LogUtil.msggenThread2LoggerDEBUG("making random SSN")
    // locks
    val lock:SSNlock = new SSNlock()
    // set to store SS #'s in
    val ssnSet:scala.collection.mutable.Set[String] = scala.collection.mutable.Set[String]()
    // make 4 runnables
    val run1:SSNMakerThread = new SSNMakerThread(lock,ssnSet,0,numberOfSsns/4)
    val run2:SSNMakerThread = new SSNMakerThread(lock,ssnSet,1,numberOfSsns/4)
    val run3:SSNMakerThread = new SSNMakerThread(lock,ssnSet,2,numberOfSsns/4)
    val run4:SSNMakerThread = new SSNMakerThread(lock,ssnSet,3,numberOfSsns/4)
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
    LogUtil.msggenThread2LoggerDEBUG("Number of generated Social Security Numbers = " + ssnSet.size);
    ssnSet
  }

}