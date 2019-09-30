/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.processes

import com.scala.classes.business.SSNRandomizer
import com.scala.classes.locks.SSNlock
import com.scala.classes.utilities.LogUtil

/**
  * Thread class for generating Social Security Numbers
  * @param lock = lock object
  * @param ssnset = set of social security #s
  * @param threadNum = thread number
  * @param numberOfSsns = number of social security #s to make
  */
class SSNMakerThread(val lock: SSNlock,
                     val ssnset: scala.collection.mutable.Set[Int],
                     val threadNum:Int,
                     val numberOfSsns: Int) extends Runnable {

  /**
    * this common Set will hold all of our unique ssns
    * critical section to check this Set before adding a new SSN to it(check-then-add)
    */
  private[this] var validNum: Boolean = false
  private val maxSsnValue = 999999999

  /**
    * main run method
    */
  override def run(): Unit = {
      LogUtil.logByNum(s"${Thread.currentThread().getId} : generating social security numbers",threadNum%4)
      for(counter <- 0 until numberOfSsns) {
        validNum = false
        while (validNum == false) {
          val newNum:Int = SSNRandomizer.randomInteger(0, maxSsnValue)
          validNum = checkThenAddToSet(newNum)
        }
      }
      LogUtil.logByNum(s"${Thread.currentThread().getId} : DONE generating social security numbers",threadNum%4)
  }

  /**
    * critical section method for checking if our set already contains the generated Social Security Number
    * @param num - number to check
    * @return good
    */
  def checkThenAddToSet(num:Int):Boolean = {
    var good:Boolean = false
    // minimize the critical section
    lock.synchronized {
      if(!ssnset.contains(num)) {
        ssnset += num
        good = true
      }
    }
    // end of critical section
    if(good) {
        LogUtil.logByNum(s"${Thread.currentThread().getId} : adding : " + num + " to SSN SET",threadNum%4)
      } else {
        LogUtil.logByNum(s"${Thread.currentThread().getId} : SSN " + num + " already found in SET",threadNum%4)
      }
    good
  }
}
