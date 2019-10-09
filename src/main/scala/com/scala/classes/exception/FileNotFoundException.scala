/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.exception

/**
  * exception thrown if the primary key file is not found
  * @param s - error message to display
  */
class FileNotFoundException(s:String) extends Exception(s) {

}
