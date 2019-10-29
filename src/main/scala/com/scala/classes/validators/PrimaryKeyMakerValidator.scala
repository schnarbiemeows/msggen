/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import com.scala.classes.utilities.{Configuration, LogUtil, PropertyLoader}

object PrimaryKeyMakerValidator extends Validator {

  /**
    * main validation method

    * @return - Boolean
    */
  override def validate(): Boolean = {
    LogUtil.msggenMasterLoggerDEBUG("validating configuration for the primary key maker mode");
    val numberOfPrimaryKeys:Int = PropertyLoader.getProperty(Configuration.MODE1_NUM_PRIMARY_KEYS_TO_MAKE).toString.toInt
    val characters:String = PropertyLoader.getProperty(Configuration.MODE1_CHARACTERS).toString
    val pkLength:Int = PropertyLoader.getProperty(Configuration.MODE1_PRIMARY_LENGTH).toString.toInt
    val outputfile:String = PropertyLoader.getProperty(Configuration.MODE1_OUTPUT_FILE).toString
    if(Math.pow(characters.length.toDouble,pkLength.toDouble).toInt<numberOfPrimaryKeys) {
      LogUtil.msggenMasterLoggerDEBUG("number of possible unique combinations is less than the total number of primaries to be made");
      false
    } else {
      true
    }
  }
}
