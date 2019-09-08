/*
 * Created 2019 by Dylan Kessler
 */

package com.scala

import classes.utilities.{DateUtils, LogUtil, PropertyLoader, StringUtils}
import classes.business._
/**
  * @author ${user.name}
  */
object App {

  /**
    * main method
    *
    * @param args - argv[0] = Int: = the mode
    *             args[1] = String: = the config file name(located in /home/schnarbies/config folder)
    *
    */
  def main(args: Array[String]) {
    LogUtil.msggenMasterLoggerDEBUG("BEGIN - mssgen program");
    if(args(0)==null||args(1)==null)
    {
      if(args(0)==null) {
        LogUtil.msggenMasterLoggerDEBUG("Mode is missing, exiting program")
      } else {
        LogUtil.msggenMasterLoggerDEBUG("configuration file is not specified, exiting program");
      }
      System.exit(1)
    }
    if(!StringUtils.isInteger(args(0))) {
      LogUtil.msggenMasterLoggerDEBUG("Mode is not a number, exiting program")
      System.exit(1)
    }
    val mode: Int = Integer.parseInt(args(0))
    var config: String = args(1)
    LogUtil.msggenMasterLoggerDEBUG(s"Mode = ${mode}");
    LogUtil.msggenMasterLoggerDEBUG(s"config = ${config}\n");
    val properties = PropertyLoader.getProperties(config)
    mode match {
      case 0 => new SSNMakerMode(0,properties).run()
      case 1 => new MasterMainMode(1,properties).run()
      case 2 => new AddressMakerMode(2,properties).run()
      case 3 => new SimpleMMMode(3,properties).run()
      case 4 => new StreamingMessagesMode(4,properties).run()
    }
    LogUtil.msggenMasterLoggerDEBUG("END - mssgen program");
    val endTime:Long = DateUtils.getFinalProgramRunTime()
    LogUtil.logTime(s"Total Program Run Time = ${endTime} milliseconds")
  }
}
