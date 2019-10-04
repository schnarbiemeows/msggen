/*
 * Created 2019 by Dylan Kessler 
 */

package com.scala.classes.processes

import com.scala.classes.business.PrimaryKeyRandomizer
import com.scala.classes.locks.PrimaryKeyLock
import com.scala.classes.utilities.LogUtil

/**
  * Thread class for generating primary keys
  * @param lock = lock object
  * @param primaryKeySet = set of primary keys
  * @param threadNum = thread number
  * @param numberOfPrimaries = number of primary keys to make
  * @param characters = all the possible characters that could be in a primary key
  * @param primarylength = length of each primary key
  */
class PrimaryKeyMakerThread(val lock: PrimaryKeyLock,
                            val primaryKeySet: scala.collection.mutable.Set[String],
                            val threadNum:Int,
                            val numberOfPrimaries: Int,
                            val characters:String,
                            val primarylength:Int) extends Runnable {

  /**
    * this common Set will hold all of our unique primary keys
    * critical section to check this Set before adding a new PrimaryKey to it(check-then-add)
    */
  private[this] var validNum: Boolean = false

  /**
    * main run method
    */
  override def run(): Unit = {
    LogUtil.logByNum(s"${Thread.currentThread().getId} : generating primary keys",threadNum%4)
    for(counter <- 0 until numberOfPrimaries) {
      validNum = false
      while (validNum == false) {
        val newPrimaryKey:String = PrimaryKeyRandomizer.randomPrimaryKey(characters, primarylength)
        validNum = checkThenAddToSet(newPrimaryKey)
      }
    }
    LogUtil.logByNum(s"${Thread.currentThread().getId} : DONE generating primary keys",threadNum%4)
  }

  /**
    * critical section method for checking if our set already contains the generated primary key
    * @param pk - number to check
    * @return good
    */
  def checkThenAddToSet(pk:String):Boolean = {
    var good:Boolean = false
    // minimize the critical section
    lock.synchronized {
      if(!primaryKeySet.contains(pk)) {
        primaryKeySet += pk
        good = true
      }
    }
    // end of critical section
    if(good) {
      LogUtil.logByNum(s"${Thread.currentThread().getId} : adding : ${pk} to PrimaryKey SET",threadNum%4)
    } else {
      LogUtil.logByNum(s"${Thread.currentThread().getId} : PrimaryKey ${pk} already found in PrimaryKey SET",threadNum%4)
    }
    good
  }
}
