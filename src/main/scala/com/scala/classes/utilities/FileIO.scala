/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

import java.io.{BufferedWriter, File, FileWriter}
import java.util.{Iterator, Properties}

import com.google.gson.Gson
import com.scala.classes.posos._
import org.apache.poi.ss.usermodel._

import scala.collection.JavaConversions._
import scala.collection.mutable

/**
  * this Object handles File Input and Output
  */
object FileIO {

  val accountIdLength:Int = 11
  val ssnLength = 9
  /**
    * method to write social security output to a file
    * @param ssns
    * @param filepath
    */
  def outputToFile(ssns: List[Int], filepath: String): Unit = {
    LogUtil.msggenThread2LoggerDEBUG("writing SSNs to a file")
    LogUtil.msggenThread1LoggerDEBUG("file path : " + filepath)
    var outfile:BufferedWriter = null
    try {
      outfile = new BufferedWriter(new FileWriter(new File(filepath),true))
      for(num <- ssns) {
        outfile.write(NumUtility.padIntToString(num,ssnLength) + "\n")
      }
    }
    catch {
      case e: Exception => {
        LogUtil.msggenThread1LoggerDEBUG("there was an issue writing SSNs to a file")
        e.printStackTrace()
      }
    }
    finally {
      LogUtil.msggenThread1LoggerDEBUG("closing our SSN file")
      outfile.close()
    }
  }

  def writeGenericRecordToFile(records: Array[Record], filepath: String,properties:Properties): Unit = {
    LogUtil.msggenMasterLoggerDEBUG("writing generic records to a file")
    LogUtil.msggenMasterLoggerDEBUG(s"file path : ${filepath}")
    val fileType = properties.get(Configuration.MODE4_OUTPUT_FILE_TYPE).toString.toLowerCase
    LogUtil.msggenMasterLoggerDEBUG(s"file path : ${fileType}")
    var outfile:BufferedWriter = null
    try {
      //LogUtil.msggenMasterLoggerDEBUG("HERE !")
      outfile = new BufferedWriter(new FileWriter(new File(filepath+"."+fileType),true))
      //LogUtil.msggenMasterLoggerDEBUG("HERE !! ")
      if(records==null) {
        LogUtil.msggenMasterLoggerDEBUG("records is NULL !! ")
      }
      LogUtil.msggenMasterLoggerDEBUG(s"records size is : ${records.length} ")
      for(record <- records) {
        if(Configuration.CSV.equals(fileType)) {
          outfile.write(record.toCSV() + "\n")
        } else if(Configuration.JSON.equals(fileType)) {
          /*if(record==null) {
            LogUtil.msggenMasterLoggerDEBUG("record is NULL !! ")
          } else {
            LogUtil.msggenMasterLoggerDEBUG("record is not null")
            LogUtil.msggenMasterLoggerDEBUG(s"${record.toJSON()}")
          }*/
          outfile.write(record.toJSON() + "\n")
        }
        else {
          outfile.write(record.toString + "\n")
        }
      }
    }
    catch {
      case e: Exception => {
        LogUtil.msggenMasterLoggerDEBUG("there was an issue writing generic records to a file")
        e.printStackTrace()
      }
    }
    finally {
      LogUtil.msggenMasterLoggerDEBUG("closing our generic records file")
      outfile.close()
    }
  }
  /**
    * method to pull the keys from the finalMap(account IDs), and output them to a file
    * for later message generation
    * @param finalMap = our final map of primaries, spouses, and dependents
    * @param filepath = the absolute path of the output file
    */
  def outputAccountIdsToFile(finalMap:mutable.HashMap[Long, SimpleMemberAddressWrapper], filepath: String): Unit = {
    val runStart = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("writing Account IDs to a file")
    LogUtil.msggenMasterLoggerDEBUG("file path : " + filepath)
    var outfile:BufferedWriter = null
    try {
      outfile = new BufferedWriter(new FileWriter(new File(filepath),false))
      val people:Iterator[Long] = finalMap.keysIterator
      while(people.hasNext) {
        val item:Long = people.next()
        outfile.write(s"${NumUtility.padIntToString(item,accountIdLength)}\n")
      }
    }
    catch {
      case e: Exception => {
        LogUtil.msggenMasterLoggerDEBUG("there was an issue writing Account IDs to a file")
        e.printStackTrace()
      }
    }
    finally {
      LogUtil.msggenMasterLoggerDEBUG("closing our Account IDs file")
      outfile.close()
      val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
      LogUtil.logTime(s"outputAccountIdsToFile() method time = ${runEnd._1} milliseconds")
    }
  }

  /**
    * method to output the values from the finalMap to a file
    * @param finalMap = our final map of primaries, spouses, and dependents
    * @param filetype = the type of file to output
    * @param filepath = the absolute path of the output file
    */
  def writeMemberAndAddressFiles(finalMap:mutable.HashMap[Long, SimpleMemberAddressWrapper], filetype:String, filepath: String*): Unit = {
    val runStart = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("writing to the Member and Address files")
    var memberFp:String = filepath(0)
    var addressFp:String = filepath(1)
    LogUtil.msggenMasterLoggerDEBUG(s"Member file path : ${memberFp}")
    LogUtil.msggenMasterLoggerDEBUG(s"Address file path : ${addressFp}")
    LogUtil.msggenMasterLoggerDEBUG(s"file type : ${filetype}")
    var memberoutfile:BufferedWriter = null
    var addressoutfile:BufferedWriter = null
    try {
      memberoutfile = new BufferedWriter(new FileWriter(new File(memberFp),false))
      addressoutfile = new BufferedWriter(new FileWriter(new File(addressFp),false))
      val people:Iterator[SimpleMemberAddressWrapper] = finalMap.valuesIterator
      while(people.hasNext) {
        val item: SimpleMemberAddressWrapper = people.next()
        val memberdata:SimpleMemberRecord = item.simpleMember
        val addressdata:SimpleAddressRecord = item.simpleAddressRecord
        if(filetype=="JSON") {
          val gson = new Gson
          memberoutfile.write(s"${gson.toJson(memberdata)}\n")
          addressoutfile.write(s"${gson.toJson(addressdata)}\n")
        } else {
          memberoutfile.write(s"${memberdata.toCSV()}\n")
          addressoutfile.write(s"${addressdata.toCSV()}\n")
        }
      }
    }
    catch {
      case e: Exception => {
        LogUtil.msggenMasterLoggerDEBUG("there was an issue writing Account IDs to a file")
        e.printStackTrace()
      }
    }
    finally {
      LogUtil.msggenMasterLoggerDEBUG("closing our Account IDs file")
      memberoutfile.close()
      addressoutfile.close()
      val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
      LogUtil.logTime(s"writeMemberAndAddressFiles() method time = ${runEnd._1} milliseconds")
    }
  }

  /**
    * method to read in an excel spreadsheet template and populate a RecordsTemplate
    * @param records = RecordsTemplate to populate
    */
  def readInSpreadsheet(records:RecordsTemplate):Unit = {
    val runStart = DateUtils.nowTime()
    var runStartLocal = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("entering readInSpreadsheet() method")
    val sourceFile:String = records.sourcefile
    try {
      /**
        * open and read in the excel template
        */
      LogUtil.msggenMasterLoggerDEBUG("attempting to open workbook")
      val workbook = WorkbookFactory.create(new File(sourceFile))
      val formatter = new DataFormatter()
      LogUtil.msggenMasterLoggerDEBUG("opened workbook")
      val sheet1 = workbook.getSheetAt(0)
      LogUtil.msggenMasterLoggerDEBUG("reading in the Column names")

      /**
        * load in the header Rows:
        * field names
        * data types
        * formats
        */
      /**
        * field names
        */
      val headerIterator = sheet1.getRow(0).cellIterator()
      val fieldsAndCounter:Tuple2[Int,Array[String]] = getFieldsTypesOrFormats(headerIterator,formatter)
      var runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
      LogUtil.logTime(s"reading in the Column names took => ${runEndLocal._1} milliseconds")
      runStartLocal = runEndLocal._2
      records.fields = fieldsAndCounter._2
      val numberOfColumns:Int = fieldsAndCounter._1
      LogUtil.msggenMasterLoggerDEBUG("reading in the Data Types of each Column")
      /**
        * field data types
        */
      val dataTypeIterator = sheet1.getRow(1).cellIterator()
      records.dataTypes = getFieldsTypesOrFormats(dataTypeIterator,formatter)._2
      runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
      LogUtil.logTime(s"reading in the Data Types of each Column took => ${runEndLocal._1} milliseconds")
      runStartLocal = runEndLocal._2
      LogUtil.msggenMasterLoggerDEBUG("reading in the Data Formatting Information for each Column")
      /**
        * field formats
        */
      val formatIterator = sheet1.getRow(2).cellIterator()
      records.dataFormats = getFieldsTypesOrFormats(formatIterator,formatter)._2
      runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
      LogUtil.logTime(s"reading in the Data Formatting Information took => ${runEndLocal._1} milliseconds")
      runStartLocal = runEndLocal._2

      //check(records) // <- test for checking the header information
      /**
        *
        * load in the possible values that each field could have
        */
      LogUtil.msggenMasterLoggerDEBUG("reading in the Data Value Information for each Column")
      records.dataQualifiers = getDataValues(sheet1, numberOfColumns, formatter)
      runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
      LogUtil.logTime(s"reading in the Data Value Information took => ${runEndLocal._1} milliseconds")
      runStartLocal = runEndLocal._2

      //checkValues(records) // <- test for checking the dataValues information
      /**
        * close the workbook
        * TODO - see if we can somehow run the template validator in parallel with the closing of the
        * notebook, since closing the notebook takes so long
        */
      workbook.close()
      runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
      LogUtil.logTime(s"closing the Workbook took => ${runEndLocal._1} milliseconds")
      runStartLocal = runEndLocal._2
    }
    catch {
      case e: Exception => {
        LogUtil.msggenMasterLoggerERROR("there was an issue reading in the spreadsheet")
        e.printStackTrace()
      }
    }
    finally {
      val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
      LogUtil.logTime(s"readInSpreadsheet() method time = ${runEnd._1} milliseconds")
    }
  }

  /**
    * method to get the fields,fieldTypes, and fieldFormats arrays
    * Row(0) = fields
    * Row(1) = fieldTypes
    * Row(2) = FieldFormats
    * @param cellIterator = Row iterator for all of the cells in a Row
    * @param formatter = DataFormatter needed by the spreadsheet object
    * @return (Int,Array(String))
    */
  def getFieldsTypesOrFormats(cellIterator: Iterator[Cell], formatter:DataFormatter):Tuple2[Int,Array[String]] = {
    var firstItem:Boolean = true
    var listOfFieldsStr:String = ""
    var counter:Int = 0
    while(cellIterator.hasNext) {
      var item:Cell = cellIterator.next()
      if(firstItem) {
        listOfFieldsStr += formatter.formatCellValue(item)
        firstItem=false
      } else {
        listOfFieldsStr += ","+formatter.formatCellValue(item)
      }
      counter += 1
    }
    (counter,listOfFieldsStr.split(","))
  }

  /**
    * method to populate the dtaValues double array of the RecordTemplate
    * this Double Array holds all of the possible values that each field could have
    * how it works:
    * create a String Array of length numberOfColumns
    * discard any cells whose row number is 2 or less; these first 3 rows contain
    * header information
    * iterate through each row and for each cell, append its value to the appropriate
    * String indexed in the String Array by the column number
    * the tilde "~" is used as the delimiter here
    * finally, iterate through the String Array, an add the split("~") to the same index
    * in the records.fieldValues Array
    *
    * @param sheet1 = Sheet object needed by the spreadsheet object
    * @param numberOfColumns = number of String arrays the we need
    * @param formatter = the data formatter needed by the spreadsheet object
    * @return Array(Array(String))
    */
  def getDataValues(sheet1:Sheet, numberOfColumns:Int, formatter:DataFormatter): Array[Array[String]] = {
    /**
      * TODO - this program will crash if any cells of the first row are blank
      * fix this
      */
    var dataValues:Array[Array[String]] = new Array(numberOfColumns)
    var arrayOfString:Array[String] = new Array[String](numberOfColumns)
    // initialize our Strings
    for(i <- 0 until arrayOfString.length) { arrayOfString(i)=""}
    for {
      row <- sheet1
      cell <- row } {
      if(cell.getRow.getRowNum>2) {
        if (arrayOfString(cell.getColumnIndex) == "") {
          arrayOfString(cell.getColumnIndex) += formatter.formatCellValue(cell)
        }
        else {
          arrayOfString(cell.getColumnIndex) += "~" + formatter.formatCellValue(cell)
        }
      }
    }
    for(j <- 0 until arrayOfString.length) {
      dataValues(j) = arrayOfString(j).split("~")
    }
    dataValues
  }
  def check(records:RecordsTemplate):Unit = {
    val fields = records.fields
    val datatypes = records.dataTypes
    val dataformats = records.dataFormats
    val fieldlength = fields.length
    for(i <- 0 until fieldlength) {
      print(fields(i)+" ")
    }
    println()
    for(i <- 0 until fieldlength) {
      print(datatypes(i)+" ")
    }
    println()
    for(i <- 0 until fieldlength) {
      print(dataformats(i)+" ")
    }
    println()
  }

  def checkValues(records:RecordsTemplate):Unit = {
    val dataValuesLength:Int = records.dataQualifiers.length
    for(i <- 0 until dataValuesLength) {
      val items = records.dataQualifiers(i)
      val itemLength:Int = items.length
      for(j <- 0 until itemLength) {
        print(items(j)+" ")
      }
      println()
    }
  }

  def readInExternalFiles(record: RecordsTemplate, properties: Properties):Boolean = {
    val runStart = DateUtils.nowTime()
    var runStartLocal = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("entering readInSpreadsheet() method")
    // TODO
    val runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
    LogUtil.logTime(s"readInExternalFiles took => ${runEndLocal._1} milliseconds")
    true
  }
}
