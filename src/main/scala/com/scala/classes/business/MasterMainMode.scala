/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business
import java.util.Properties
import com.scala.classes.utilities.LogUtil

import scala.collection.JavaConversions._
import com.scala.classes.utilities.PropertyLoader

/**
  * mode for creating a Master Member file
  */
class MasterMainMode(val mode: Int, val properties: Properties) extends Mode {
  /**
    * main run method, not being used at the moment
    */
  override def run(): Unit = {
    LogUtil.msggenThread1LoggerDEBUG("inside MasterMainMode");
    for (key <- properties.keySet) {
      val keyStr:String = key.asInstanceOf[String]
      LogUtil.msggenThread1LoggerDEBUG(keyStr + " = " + properties.getProperty(keyStr))
    }
  }
}
