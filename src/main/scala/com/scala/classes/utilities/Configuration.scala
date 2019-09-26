/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

/**
  * object to use for storing Global Constants
  */
object Configuration {

  val PROPERTY_FILE_LOCATION = "config.properties"
  val SYSTEM = "system"
  val DELIMITTER1 = ","
  val EQUALS = "="
  val CSV = "csv"
  val JSON = "json"
  val DEFAULT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
  val ALPHA_NUMERIC_STRING:String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
  val LETTERS:String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
  val NUMBERS:String = "0123456789"
  val SSN_FILE_LOC:String = "common.ssn.file.loc"
  val SSN_OUTPUT_FILE:String = "mode0.ssn.file"
  val SSN_NUMBER_TO_MAKE = "mode0.ssn.num"
  val MM_FILE_LOC:String = "mode1.mm.file"
  val MM_FILE_FORMAT:String = "mode1.mm.format"
  val MM_NUM_RECORDS:String = "mode1.mm.numberofrecords"
  val ADDR_FILE_LOC:String = "mode2.address.file"
  val ADDR_FILE_FORMAT:String = "mode2.address.format"
  val ADDR_NUM_RECORDS:String = "mode2.address.numberofrecords"
  val MODE3_NUM_RECORDS:String = "mode3.numberofrecords"
  val PRIMARY_PERCENT = "mode3.primary.percent"
  val SPOUCE_PERCENT = "mode3.spouse.percent"
  val CHILD_PERCENT = "mode3.child.percent"
  val MIDDLENAME_PERCENT = "mode3.middlename.percent"
  val LINE2_ADDR_PERCENT = "mode3.addressline2.percent"
  val PRIMARY_MINAGE = "mode3.primary.minage"
  val PRIMARY_MAXAGE = "mode3.primary.maxage"
  val SPOUSE_AGERANGE = "mode3.spouse.agerange"
  val CHILD_MAXAGE = "mode3.child.maxage"
  val MODE4_NUM_RECORDS = "mode4.numberofrecordsperfile"
  val MODE4_NUM_FILES = "mode4.numfiles"
  val MODE4_SRC_FILE = "mode4.sourcefile"
  val MODE4_EXTRNL_FILE = "mode4.externalfile"
  val MODE4_OUTPUT_FILE = "mode4.outputfile.folder"
  val MODE4_FILE_PREPENDER = "mode4.outputfile.prepender"
  val MODE4_STRING_DATA_RULE = "mode4.fieldname.possiblecharacters"
  val MODE4_NUM_THREADS = "mode4.numthreads"
  val MODE4_OUTPUT_FILE_TYPE = "mode4.outputfile.type"

  // defaults
  val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
  val DEFAULT_DATE_TIME_FORMAT = "yy-MM-dd HH:mm:ss"
  val DEFAULT_START_DATE = "1900-01-01"
  val DEFAULT_START_DATE_TIME = "1900-01-01 00:00:00"
  val DEFAULT_END_DATE = "2100-12-31"
  val DEFAULT_END_DATE_TIME = "2100-12-31 00:00:00"
}
