/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import com.scala.classes.actors.controllers.RecordMakerController
import com.scala.classes.posos.GenericRecordsTemplate
import com.scala.classes.utilities._
import com.scala.classes.validators.ExcelDataSheetValidator

/**
  * mode for the generation of records of any kind of random data, as described by an excel
  * spreadsheet whose location is specified by the mode4.sourcefile property
  *
  * @param mode - mode of the program
  */
class StreamingMessagesMode(val mode: Int) extends Mode {

  /**
    * our records to generate and export
    */
  var records:GenericRecordsTemplate = new GenericRecordsTemplate()
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
    // 2. validate the data
    val templateValidated:Boolean = new ExcelDataSheetValidator(mode,records).validate()
    if(templateValidated) {
      LogUtil.msggenMasterLoggerDEBUG("template validated")
      // 3. read in any external files for external fields
      if(FileIO.readInExternalFiles(records)) {
        // commence the file creation
        val recordMaker:RecordMakerController= new RecordMakerController(records)
        val success:Boolean = recordMaker.generateRecords()
        if(success&&(mode==5||mode==7)) {
          LogUtil.msggenMasterLoggerDEBUG("creating Hive table script")
          val hiveList:List[String] = HiveTableCreator.makeTableArray(records)
          val filepath = PropertyLoader.getProperty(Configuration.MODE5_HIVE_OUTPUTFILE)
          FileIO.outputAnyListToFile(hiveList,filepath)
          LogUtil.msggenMasterLoggerDEBUG("finished creating Hive table script")
        }
      }
    } else {
      LogUtil.msggenMasterLoggerDEBUG("template not validated")
    }
    LogUtil.msggenMasterLoggerDEBUG("DONE - generating streaming message information to file")
    val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
    LogUtil.logTime(s"StreamingMessagesMode run() method time = ${runEnd._1} milliseconds")
  }
}
