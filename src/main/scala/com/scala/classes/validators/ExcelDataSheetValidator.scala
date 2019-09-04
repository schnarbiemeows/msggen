/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

import com.scala.classes.posos.{GenericRecordsTemplate, RecordsTemplate}
import com.scala.classes.utilities.LogUtil

class ExcelDataSheetValidator(template: RecordsTemplate) extends Validator {

  override def validate(): Boolean = {
    LogUtil.logValidationMessage("validating the input template")
    var isValidated = true

    isValidated
  }

  def validateLengthsOfThreeHeaderRows():Boolean = {
    var isValidated = true

    isValidated
  }

  def validateNoRowLengthsExceedHeaderLength():Boolean = {
    var isValidated = true

    isValidated
  }

  def validateTheDataTypes():Boolean = {
    var isValidated = true

    isValidated
  }

  def validateDataFormats():Boolean = {
    var isValidated = true

    isValidated
  }

  def validateValuesData():Boolean = {
    var isValidated = true

    isValidated
  }

  def validatePresenceOfExternalFiles():Boolean = {
    var isValidated = true

    isValidated
  }

}
