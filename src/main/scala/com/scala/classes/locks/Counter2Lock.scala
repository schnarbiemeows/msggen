/*
 * Created 2019 by Dylan Kessler
 */

/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.locks

/**
  * Lock with a counter which keeps track of how many records we are making
  */
class Counter2Lock(var counter:Int=0) {

  /**
    * counter incrementer
    */
  def incrementCounter(): Unit = {
    counter = counter+1
  }
}
