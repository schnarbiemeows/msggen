/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.exception

/**
  * exception thrown if the data qualifier listed for a given formatting rule
  * does not have an acceptable value
  * @param s - error message to display
  */
class InvalidDataQualifierException(s:String) extends Exception(s) {

}
