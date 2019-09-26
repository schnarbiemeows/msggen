/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.exception

/**
  * exception thrown if any of the rows after the first 3 have columns past
  * the column length of the first 3(the meta-data header rows)
  * not currently being used
  * @param s - error message to display
  */
class RowLengthsExceedsHeaderLengthException(s:String) extends Exception(s) {

}
