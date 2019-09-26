/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.exception

/**
  * exception thrown if field name is invalid
  * @param s - error message to display
  */
class InvalidFieldNameException(s:String) extends Exception(s) {

}
