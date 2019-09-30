/*
 * Created 2019 by Dylan Kessler
 */

package com.scala

import com.scala.classes.business._
import com.scala.classes.utilities._
/**
  * @author Dylan Kessler
  */
object App {

  /**
    * main method
    * @param args -
    * args[0] = String: = the full path to the main config file
    */
  def main(args: Array[String]) {
    LogUtil.msggenMasterLoggerDEBUG("BEGIN - mssgen program");
    if(args(0)==null) {
      LogUtil.msggenMasterLoggerDEBUG("config file location is missing, exiting program")
      System.exit(1)
    }
    val config: String = args(0)
    LogUtil.msggenMasterLoggerDEBUG(s"config = ${config}\n");
    val properties = PropertyLoader.getProperties(config)
    val mode:Int = properties.getProperty(Configuration.MODE).toInt
    LogUtil.msggenMasterLoggerDEBUG(s"Mode = ${mode}");
    mode match {
      case 0 => new SSNMakerMode(0,properties).run()
      case 3 => new SimpleMMMode(3,properties).run()
      case 4 => new StreamingMessagesMode(4,properties).run()
      case 5 => new StreamingMessagesMode(5,properties).run()
      case 6 => new HqlMakerMode(6,properties).run()
      case default => println(s"mode ${default} is not a valid mode, so nothing to do here")
    }
    LogUtil.msggenMasterLoggerDEBUG("END - mssgen program");
    val endTime:Long = DateUtils.getFinalProgramRunTime()
    LogUtil.logTime(s"Total Program Run Time = ${endTime} milliseconds")
  }
}
