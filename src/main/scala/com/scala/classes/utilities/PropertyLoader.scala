/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.utilities

import java.io.{FileInputStream, IOException}
import java.util.Properties
/**
  * object for loading the contents of the config file located at /home/ubuntu/config/
  */
object PropertyLoader {

  /**
    * method for reading in the properties from a text file
    * @param configFileName - name of the config file to load
    * @return - Properties object of all of the file properties
    */
  def getProperties(configFileName:String): Properties = {
    var fullpath: String = null
    try {
      val prop = new Properties()
      fullpath = adjustConfigFilePath(configFileName)
      println(s"loading properties from file: ${fullpath}")
      prop.load(new FileInputStream(fullpath))
      println("properties loaded")
      setLogger(prop)
      prop
    } catch {
      case e: IOException =>
        //LogUtil.msggenThread1LoggerERROR(s"error loading properties from file: ${fullpath}")
        e.printStackTrace()
        sys.exit(1)
    }
  }

  /**
    * method to detect if the operating system is windows, and, if so, to
    * replace any "\" in the file path with "\\"
    * @param configFileName - full path to the config file
    * @return - adjusted config file path
    */
  def adjustConfigFilePath(configFileName:String):String = {
    val osName = System.getProperty("os.name")
    var configFileNameadjusted:String = configFileName
    if(osName.toLowerCase.contains("windows")) {
      println("windows file was found")
      val configFileNameadjusted = configFileName.replace("\\\\","\\").replace("\\","\\\\")
    }
    println(s"file name = $configFileNameadjusted")
    configFileNameadjusted
  }

  /**
    * method to detect if the operating system is windows, and, if so, to
    * replace any "\" in the file path with "\\"
    * @param configFileName - full path to the config file
    * @return - adjusted config file path
    */
  def revAdjustConfigFilePath(configFileName:String):String = {
    val osName = System.getProperty("os.name")
    var configFileNameadjusted:String = configFileName
    if(osName.toLowerCase.contains("windows")) {
      println("windows file was found")
      configFileNameadjusted = configFileName.replaceAll("\\\\","\\\\\\\\")
    }
    println(s"file name = $configFileNameadjusted")
    configFileNameadjusted
  }

  def setLogger(prop: Properties): Unit = {
    val rootLogger:String = prop.getProperty(Configuration.ROOT_LOGGER).toString
    System.setProperty("rootlogger",rootLogger)
  }
}
