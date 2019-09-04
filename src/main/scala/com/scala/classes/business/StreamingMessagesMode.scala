/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import java.util.Properties

import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.utilities.{DateUtils, FileIO, LogUtil}

/**
  *
  */
class StreamingMessagesMode(val mode: Int, val properties: Properties) extends Mode {

  /**
    * our records to generate and export
    */
  var records:GenericRecordsTemplate = new GenericRecordsTemplate(properties)
  /**
    * main run method
    */
  override def run(): Unit = {
    var runStart = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("inside StreamingMessagesMode");

    // steps:
    //
    // 1. read in excel spreadsheet data
    FileIO.readInSpreadsheet(records)
    //
    LogUtil.msggenMasterLoggerDEBUG("DONE - generating streaming message information to file")
    val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
    LogUtil.logTime(s"StreamingMessagesMode run() method time = ${runEnd._1} milliseconds")
  }
}
