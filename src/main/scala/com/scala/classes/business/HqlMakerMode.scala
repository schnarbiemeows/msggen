/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import java.util.Properties

import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.utilities.{Configuration, DateUtils, FileIO, LogUtil}
import com.scala.classes.validators.ExcelDataSheetValidator

class HqlMakerMode(val mode: Int, val properties: Properties) extends Mode {

  /**
    * our records to generate and export
    */
  var records:GenericRecordsTemplate = new GenericRecordsTemplate(properties)
  /**
    * main run method
    */
  override def run(): Unit = {
    var runStart = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("inside HqlMakerMode");

    // steps:
    //
    // 1. read in excel spreadsheet data
    FileIO.readInSpreadsheet(records)
    // 2. validate the data
    val templateValidated:Boolean = new ExcelDataSheetValidator(mode,records).validate(properties)
    if(templateValidated) {
      LogUtil.msggenMasterLoggerDEBUG("template validated")
      LogUtil.msggenMasterLoggerDEBUG("creating Hive table script")
      val hiveList:List[String] = HiveTableCreator.makeTableArray(records,properties)
      val filepath = properties.getProperty(Configuration.MODE5_HIVE_OUTPUTFILE)
      FileIO.outputAnyListToFile(hiveList,filepath)
      LogUtil.msggenMasterLoggerDEBUG("finished creating Hive table script")
    } else {
      LogUtil.msggenMasterLoggerDEBUG("template not validated")
    }
    LogUtil.msggenMasterLoggerDEBUG("DONE - generating hql script and writing it to file")
    val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
    LogUtil.logTime(s"HqlMakerMode run() method time = ${runEnd._1} milliseconds")
  }
}
