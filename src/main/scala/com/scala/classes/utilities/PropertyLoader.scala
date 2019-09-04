/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

import java.io.{FileInputStream, IOException}
import java.util.Properties

import com.scala.classes.utilities.LogUtil
/**
  * object for loading the contents of the config file located at /home/ubuntu/config/
  */
object PropertyLoader {

  /**
    * TODO: need to change this to deduce from the OS type
    */
  val folderPath:String = "C:\\home\\schnarbies\\config\\"

  /**
    * method for reading in the properties from a text file
    * @param configFileName - name of the config file to load
    * @return - Properties object of all of the file properties
    */
  def getProperties(configFileName:String): Properties = {
    var fullpath: String = null
    try {
      val prop = new Properties()
      fullpath = folderPath + configFileName
      LogUtil.msggenMasterLoggerDEBUG(s"loading properties from file: ${fullpath}")
      prop.load(new FileInputStream(fullpath))
      println("properties loaded")
      prop
    } catch {
      case e: IOException =>
        LogUtil.msggenThread1LoggerERROR(s"error loading properties from file: ${fullpath}")
        e.printStackTrace()
        sys.exit(1)
    }
  }
}
