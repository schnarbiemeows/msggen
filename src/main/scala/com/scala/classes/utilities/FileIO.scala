/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

import java.io.{BufferedWriter, File, FileWriter}
import java.time.LocalTime
import java.util.{Iterator, Properties}

import com.google.gson.Gson
import com.scala.classes.posos._
import com.scala.classes.validators.StringQualifiersValidator.filterQualifiers
import org.apache.poi.ss.usermodel._

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.io.Source

/**
  * this Object handles File Input and Output
  */
object FileIO {

  val accountIdLength:Int = 11

  /**
    * method to write any list of items to a file
    * @param input - list of items
    * @param filepath - path
    * @return - success indicator
    */
  def outputAnyListToFile(input: List[Any], filepath: String): Boolean = {
    val runStart = DateUtils.nowTime()
    var runStartLocal = DateUtils.nowTime()
    var runEndLocal:Tuple2[Long, LocalTime] = (0,runStartLocal)
    LogUtil.msggenMasterLoggerDEBUG("writing List to a file")
    LogUtil.msggenMasterLoggerDEBUG("file path : " + filepath)
    var outfile:BufferedWriter = null
    try {
      outfile = new BufferedWriter(new FileWriter(new File(filepath),false))
      runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
      LogUtil.logTime(s"opening the output file took => ${runEndLocal._1} milliseconds")
      runStartLocal = runEndLocal._2
      for(item <- input) {
        outfile.write(s"${item.toString} \n")
      }
      runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
      LogUtil.logTime(s"writing to the output file took => ${runEndLocal._1} milliseconds")
      runStartLocal = runEndLocal._2
      true
    }
    catch {
      case e: Exception => {
        LogUtil.msggenMasterLoggerDEBUG("there was an issue writing the List to a file")
        e.printStackTrace()
        false
      }
    }
    finally {
      LogUtil.msggenMasterLoggerDEBUG("closing our output file")
      outfile.close()
      runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
      LogUtil.logTime(s"closing the output file took => ${runEndLocal._1} milliseconds")
      runStartLocal = runEndLocal._2
      val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
      LogUtil.logTime(s"outputAnyListToFile() method time = ${runEnd._1} milliseconds")
    }
  }

  /**
    * method to write an array of records out to either csv files or json files
    * @param records - array of records to process
    * @param filepath - full path to the file
    * @param properties - singleton Properties object
    */
  def writeGenericRecordToFile(records: Array[Record], filepath: String,properties:Properties): Unit = {
    val runStart = DateUtils.nowTime()
    var runStartLocal = DateUtils.nowTime()
    var runEndLocal:Tuple2[Long, LocalTime] = (0,runStartLocal)
    LogUtil.msggenMasterLoggerDEBUG("writing generic records to a file")
    LogUtil.msggenMasterLoggerDEBUG(s"file path : ${filepath}")
    val fileType = properties.get(Configuration.MODE4_OUTPUT_FILE_TYPE).toString.toLowerCase
    LogUtil.msggenMasterLoggerDEBUG(s"file path : ${fileType}")
    var outfile:BufferedWriter = null
    try {
      LogUtil.msggenMasterLoggerDEBUG("opening BufferedWriter")
      outfile = new BufferedWriter(new FileWriter(new File(filepath+"."+fileType),true))
      runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
      LogUtil.logTime(s"opening the output file took => ${runEndLocal._1} milliseconds")
      runStartLocal = runEndLocal._2
      LogUtil.msggenMasterLoggerDEBUG("BufferedWriter open")
      if(records==null) {
        LogUtil.msggenMasterLoggerDEBUG("there are no records to process !! ")
      }
      LogUtil.msggenMasterLoggerDEBUG(s"records size is : ${records.length} ")
      for(record <- records) {
        if(Configuration.CSV.equals(fileType)) {
          outfile.write(record.toCSV() + "\n")
        } else if(Configuration.JSON.equals(fileType)) {
          outfile.write(record.toJSON() + "\n")
        }
        else {
          outfile.write(record.toString + "\n")
        }
      }
      runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
      LogUtil.logTime(s"writing to the output file took => ${runEndLocal._1} milliseconds")
      runStartLocal = runEndLocal._2
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
      runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
      LogUtil.logTime(s"closing the output file took => ${runEndLocal._1} milliseconds")
      runStartLocal = runEndLocal._2
      val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
      LogUtil.logTime(s"writeGenericRecordToFile() method time = ${runEnd._1} milliseconds")
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
      val fieldsAndCounter:Tuple2[Int,Array[String]] = getFieldsTypesOrFormats(headerIterator,formatter,",")
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
      records.dataTypes = getFieldsTypesOrFormats(dataTypeIterator,formatter,",")._2
      runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
      LogUtil.logTime(s"reading in the Data Types of each Column took => ${runEndLocal._1} milliseconds")
      runStartLocal = runEndLocal._2
      LogUtil.msggenMasterLoggerDEBUG("reading in the Data Formatting Information for each Column")
      /**
        * field formats
        */
      val formatIterator = sheet1.getRow(2).cellIterator()
      records.dataFormats = getFieldsTypesOrFormats(formatIterator,formatter,":")._2
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
    * @param delimiter - temporary delimiter for creating an array from a list of cell values
    * @return (Int,Array(String))
    */
  def getFieldsTypesOrFormats(cellIterator: Iterator[Cell], formatter:DataFormatter, delimiter:String):Tuple2[Int,Array[String]] = {
    var firstItem:Boolean = true
    var listOfFieldsStr:String = ""
    var counter:Int = 0
    while(cellIterator.hasNext) {
      var item:Cell = cellIterator.next()
      if(firstItem) {
        listOfFieldsStr += formatter.formatCellValue(item)
        firstItem=false
      } else {
        listOfFieldsStr += delimiter+formatter.formatCellValue(item)
      }
      counter += 1
    }
    (counter,listOfFieldsStr.split(delimiter))
  }

  /**
    * method to populate the dataValues double array of the RecordTemplate
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
  def getDataValues(sheet1:Sheet, numberOfColumns:Int, formatter:DataFormatter): Array[ArrayBuffer[String]] = {
    /**
      * TODO - this program will crash if any cells of the first row are blank
      * fix this
      */
    var dataValues:Array[ArrayBuffer[String]] = new Array[ArrayBuffer[String]](numberOfColumns)
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
      if(arrayOfString(j).length>0) {
        dataValues(j) = arrayOfString(j).split("~").to[ArrayBuffer]
      } else {
        dataValues(j) = ArrayBuffer.empty
      }
    }
    dataValues
  }

  /**
    * test method for checking records
    * @param records - records to check
    */
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

  /**
    * test method for checking record values
    * @param records - records to check
    */
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

  /**
    * ths method will iterate through the data types, and
    * if the data type in an external data type, it will open the file and
    * download the file's contents(or a subsection of its contents)
    * into that data type's qualifiers array. Then we can handle this data type
    * the same as an Enum type
    * @param record - template that contains table information about our data
    * @param properties - singleton Properties object
    * @return - successful indicator
    */
  def readInExternalFiles(record: RecordsTemplate, properties: Properties):Boolean = {
    val runStart = DateUtils.nowTime()
    var runStartLocal = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("entering readInSpreadsheet() method")
    val fields = record.fields
    val dataTypes = record.dataTypes
    val allFormats = record.dataFormats
    val allQualifiers = record.dataQualifiers
    var resultOfValidator:Tuple2[Boolean,String] = null
    for(i <- 0 until dataTypes.length) {
      val field = fields(i)
      val dataType = dataTypes(i)
      val format = allFormats(i)
      val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataType, format)
      var qualifiers = allQualifiers(i)
      if (dataType.toLowerCase.contains("external")) {
        val filename = qualifiers(qualifiers.length-1)
        val bufferedSource = Source.fromFile(filename)
        if(filename.toLowerCase.endsWith(".csv")) {
          LogUtil.msggenMasterLoggerDEBUG("reading in .csv file")
          var counter:Int = 0
          for (line <- bufferedSource.getLines) {
            qualifiers(counter)=line  // TODO - add code to split line by "," to array and then take nth element
            counter+=1
          }
        } else if(filename.toLowerCase.endsWith(".txt")) {
          /**
            * if its a text file, we assume that there is only a single item on each line
            */
          LogUtil.msggenMasterLoggerDEBUG("reading in .txt file")
          var counter:Int = formatsThatNeedQualifierChecks.length
          var qualifiersSize:Int = qualifiers.length
          for (line <- bufferedSource.getLines) {
            if(counter<qualifiersSize) {
              qualifiers(counter)=line  // TODO - add code to split line by "," to array and then take nth element
            } else {
              qualifiers+=line
            }
            counter+=1
          }
        } else { //
          LogUtil.msggenMasterLoggerDEBUG("reading in .json file")
          for (line <- bufferedSource.getLines) {
            // TODO - add code
          }
        }
        bufferedSource.close
      }
    }
    val runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
    LogUtil.logTime(s"readInExternalFiles took => ${runEndLocal._1} milliseconds")
    true
  }

  /**
    * this method will read in the primary key file that was made using mode1
    * the file location is specified by the config value mode1.outputfile
    * @param filepath - location of the file(mode1.outputfile)
    * @return - Array of the records in the file
    */
  def simpleReadInFile(filepath:String):Array[String] = {
    var runStartLocal = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("entering readInPrimaryKeyFile() method")
    val primaryList:ListBuffer[String] = new ListBuffer[String]()
    val bufferedSource = Source.fromFile(filepath)
    for (line <- bufferedSource.getLines) {
      primaryList.add(line)
    }
    bufferedSource.close
    val runEndLocal = DateUtils.getDifferenceInMilliseconds(runStartLocal)
    LogUtil.logTime(s"readInPrimaryKeyFile took => ${runEndLocal._1} milliseconds")
    primaryList.toArray
  }
}
