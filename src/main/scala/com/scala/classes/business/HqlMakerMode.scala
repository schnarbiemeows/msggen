/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.utilities._
import com.scala.classes.validators.ExcelDataSheetValidator

class HqlMakerMode(val mode: Int) extends Mode {

  /**
    * our records to generate and export
    */
  var records:GenericRecordsTemplate = new GenericRecordsTemplate()
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
    val templateValidated:Boolean = new ExcelDataSheetValidator(mode,records).validate()
    if(templateValidated) {
      LogUtil.msggenMasterLoggerDEBUG("template validated")
      LogUtil.msggenMasterLoggerDEBUG("creating Hive table script")
      val hiveList:List[String] = HiveTableCreator.makeTableArray(records)
      val filepath = PropertyLoader.getProperty(Configuration.MODE5_HIVE_OUTPUTFILE)
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
