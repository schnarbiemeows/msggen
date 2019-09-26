/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.exception

/**
  * exception thrown if the length of all 3 header rows are not the same
  * @param s - error message to display
  */
class MismatchedColumnLengthException(s: String) extends Exception(s) {

}
