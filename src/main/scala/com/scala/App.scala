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
    //LogUtil.msggenMasterLoggerDEBUG("BEGIN - mssgen program");
    if(args.length==0) {
      //LogUtil.msggenMasterLoggerDEBUG("config file location is missing, exiting program")
      System.exit(1)
    }
    val config: String = args(0)
    //LogUtil.msggenMasterLoggerDEBUG(s"config = ${config}\n");
    PropertyLoader.getProperties(config)
    val mode:Int = PropertyLoader.getProperty(Configuration.MODE).toInt
    LogUtil.msggenMasterLoggerDEBUG(s"Mode = ${mode}");
    mode match {
      case 0 => new SSNMakerMode(0).run()
      case 1 => new PrimaryKeyMakerMode(1).run()
      case 3 => new SimpleMMMode(3).run()
      case 4 => new StreamingMessagesMode(4).run()
      case 5 => new StreamingMessagesMode(5).run()
      case 6 => new HqlMakerMode(6).run()
      case 7 => new StreamingMessagesMode(7).run()
      case 8 => new StreamingMessagesMode(8).run()
      case default => println(s"mode ${default} is not a valid mode, so nothing to do here")
    }
    LogUtil.msggenMasterLoggerDEBUG("END - mssgen program");
    val endTime:Long = DateUtils.getFinalProgramRunTime()
    LogUtil.logTime(s"Total Program Run Time = ${endTime} milliseconds")
  }
}
