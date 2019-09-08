/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.exception

/**
  * exception thrown if the data formatting rules specified for a field do not conform
  * to the valid ones for that given data type
  * @param s
  */
class InvalidDataFormatException(s:String) extends Exception(s) {

}
