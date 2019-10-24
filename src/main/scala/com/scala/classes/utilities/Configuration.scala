/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

/**
  * object to use for storing Global Constants
  */
object Configuration {

  val PROPERTY_FILE_LOCATION = "C:\\home\\schnarbies\\config\\config.properties"
  val SYSTEM = "system"
  val MODE = "mode"
  val ROOT_LOGGER = "root.log.path"
  val DELIMITTER1 = ","
  val EQUALS = "="
  val CSV = "csv"
  val JSON = "json"
  val DEFAULT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
  val ALPHA_NUMERIC_STRING:String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
  val LETTERS:String = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
  val NUMBERS:String = "0123456789"
  // mode 0 configs
  val MODE0_SSN_OUTPUT_FILE:String = "mode0.ssn.file"
  val MODE0_SSN_NUMBER_TO_MAKE = "mode0.ssn.num"
  // mode 1 configs
  val MODE1_OUTPUT_FILE = "mode1.outputfile"
  val MODE1_NUM_PRIMARY_KEYS_TO_MAKE = "mode1.numberofprimariestomake"
  val MODE1_CHARACTERS = "mode1.chars"
  val MODE1_PRIMARY_LENGTH = "mode1.length"
  // mode 3 configs
  val MODE3_MASTER_MEMBER_FILE_LOC:String = "mode3.mm.file"
  val MODE3_MEMBER_ADDRESS_FILE_LOC:String = "mode3.address.file"
  val MODE3_NUM_RECORDS:String = "mode3.numberofrecords"
  val MODE3_NUMBER_OF_PRIMARIES = "mode3.numberofprimaries"
  val MODE3_NUMBER_OF_SPOUSES = "mode3.numberofspouses"
  val MODE3_NUMBER_OF_DEPENDENTS = "mode3.numberofdependents"
  val MODE3_MIDDLENAME_PERCENT = "mode3.middlename.percent"
  val MODE3_LINE2_ADDR_PERCENT = "mode3.addressline2.percent"
  val MODE3_PRIMARY_MINAGE = "mode3.primary.minage"
  val MODE3_PRIMARY_MAXAGE = "mode3.primary.maxage"
  val MODE3_SPOUSE_AGERANGE = "mode3.spouse.agerange"
  val MODE3_CHILD_MAXAGE = "mode3.child.maxage"
  // mode 4 configs
  val MODE4_NUM_RECORDS = "mode4.numberofrecordsperfile"
  val MODE4_NUM_FILES = "mode4.numfiles"
  val MODE4_SRC_FILE = "mode4.sourcefile"
  val MODE4_EXTRNL_FILE = "mode4.externalfile"
  val MODE4_OUTPUT_FILE = "mode4.outputfile.folder"
  val MODE4_FILE_PREPENDER = "mode4.outputfile.prepender"
  val MODE4_STRING_DATA_RULE = "mode4.fieldname.possiblecharacters"
  val MODE4_NUM_THREADS = "mode4.numthreads"
  val MODE4_OUTPUT_FILE_TYPE = "mode4.outputfile.type"
  // mode 5 configs
  val MODE5_HIVE_OUTPUTFILE = "mode5.hive.outputfile"
  val MODE5_HIVE_DATABASE_NAME = "mode5.hive.database.name"
  val MODE5_HIVE_TABLE_NAME = "mode5.hive.table.name"
  val MODE5_HIVE_RAWDATA = "mode5.hive.rawdata"
  val MODE5_HIVE_EXTERNAL_TABLE = "mode5.hive.externaltable"
  val MODE5_HIVE_HDFS_LOCATION = "mode5.hive.hdfs.location"
  // mode 8 configs
  val MODE8_BOOTSTRAP_SERVER = "mode8.bootstrap.server"
  val MODE8_TOPIC_NAME = "mode8.topic.name"
  // defaults
  val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
  val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
  val DEFAULT_START_DATE = "1900-01-01"
  val DEFAULT_START_DATE_TIME = "1900-01-01 00:00:00"
  val DEFAULT_END_DATE = "2100-12-31"
  val DEFAULT_END_DATE_TIME = "2100-12-31 00:00:00"
  val DEFAULT_NOW_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"
}
