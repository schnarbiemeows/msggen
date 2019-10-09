/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import java.util.Properties

import com.scala.classes.utilities.{Configuration, LogUtil}

object PrimaryKeyMakerValidator extends Validator {

  /**
    * main validation method
    * @param properties - singleton Properties object
    * @return - Boolean
    */
  override def validate(properties:Properties): Boolean = {
    LogUtil.msggenMasterLoggerDEBUG("validating configuration for the primary key maker mode");
    val numberOfPrimaryKeys:Int = properties.get(Configuration.MODE1_NUM_PRIMARY_KEYS_TO_MAKE).toString.toInt
    val characters:String = properties.get(Configuration.MODE1_CHARACTERS).toString
    val pkLength:Int = properties.get(Configuration.MODE1_PRIMARY_LENGTH).toString.toInt
    val outputfile:String = properties.get(Configuration.MODE1_OUTPUT_FILE).toString
    if(Math.pow(characters.length.toDouble,pkLength.toDouble).toInt<numberOfPrimaryKeys) {
      LogUtil.msggenMasterLoggerDEBUG("number of possible unique combinations is less than the total number of primaries to be made");
      false
    } else {
      true
    }
  }
}
