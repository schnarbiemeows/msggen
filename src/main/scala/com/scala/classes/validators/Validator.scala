/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.validators

/**
  * trait for the handling of various validations
  */
trait Validator {

  /**
    * main validation method
    * @return
    */
  def validate():Boolean
}
