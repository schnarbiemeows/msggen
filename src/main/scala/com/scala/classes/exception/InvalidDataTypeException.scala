/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.exception

/**
  * exception thrown if a data type specified is not a valid data type
  * @param s - error message to display
  */
class InvalidDataTypeException(s:String) extends Exception(s) {

}
