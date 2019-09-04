/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.processes

import com.scala.classes.business.SSNRandomizer
import com.scala.classes.locks.SubscriberIDlock
import com.scala.classes.utilities.LogUtil

import scala.collection.mutable

/**
  * Process thread for making a list of random subscriber Ids
  * @param lock = lock object
  * @param subscriberIdSet = set of subscriber ID #s
  * @param threadNum = thread number
  * @param numberOfSubscriberIds = number of subscriber IDs to make
  */
class SubscriberIdMakerThread(val lock: SubscriberIDlock,
                              var subscriberIdSet: scala.collection.mutable.Set[Int],
                              val threadNum:Int,val numberOfSubscriberIds: Int) extends Runnable{

  /**
    * this common Set will hold all of our unique ssns
    * critical section to check this Set before adding a new SSN to it(check-then-add)
    */
  private[this] var validNum: Boolean = false
  private val maxSubscriberIdValue = 999999999

  /**
    * main thread method
    */
  override def run(): Unit = {
    LogUtil.logByNum(s"${Thread.currentThread().getId} : logNum = ${threadNum} , logNum%4 = ${threadNum%4}",threadNum%4)
    LogUtil.logByNum(s"${Thread.currentThread().getId} : generating subscriber ID numbers",threadNum%4)
    for(counter <- 0 until numberOfSubscriberIds) {
      validNum = false
      while (validNum == false) {
        val newNum:Int = SSNRandomizer.randomInteger(0, maxSubscriberIdValue)
        validNum = checkThenAddToSet(newNum)
      }
    }
    LogUtil.logByNum(s"${Thread.currentThread().getId} : DONE generating subscriber ID numbers",threadNum%4)
  }

  /**
    * critical section method to check the subscriber ID list for duplicates before adding a new subscriber ID
    * @param num = number to check
    * @return true or false
    */
  def checkThenAddToSet(num:Int):Boolean = {
    var good:Boolean = false
    // minimize the critical section
    lock.synchronized {
      if(!subscriberIdSet.contains(num)) {
        subscriberIdSet += num
        good = true
      }
    }
    // end of critical section
    if(good) {
      //LogUtil.logByNum(s"${Thread.currentThread().getId} : adding : " + num + " to subscriber ID SET",threadNum%4)
    } else {
      LogUtil.logByNum(s"${Thread.currentThread().getId} : SSN " + num + " already found in SET",threadNum%4)
    }
    good
  }
}
