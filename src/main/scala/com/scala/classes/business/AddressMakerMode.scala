/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business
import com.scala.classes.utilities.LogUtil
import java.util.Properties

import scala.collection.JavaConversions._

/**
  * mode for creating a master address file
  */
class AddressMakerMode(val mode: Int, val properties: Properties) extends Mode {

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
