/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.generators

import scala.collection.mutable.ArrayBuffer

/**
  * this trait holds the common methods to our data type generator implementers
  */
trait Generator {

  /**
    * this method generates a random value for any of the Enum or External data types
    * @param dataTypeName - the name of the particular data type we are creating
    * @param format - formatting specifications for the field
    * @param qualifiers - array of possible values
    * @return - string
    */
  def makeRandonizedExternalOrEnumDataType(dataTypeName:String, format: String, qualifiers:ArrayBuffer[String]):String = {
    val arrayLength = qualifiers.length
    var nullPercentage:Double = 0.0
    val formatsThatNeedQualifierChecks:Array[String] = filterQualifiers(dataTypeName, format)._1
    for (i <- 0 until formatsThatNeedQualifierChecks.length) {
      formatsThatNeedQualifierChecks(i) match {
        case "nullable" => nullPercentage = qualifiers(i).toDouble
      }
    }
    val index = randomInteger(formatsThatNeedQualifierChecks.length,arrayLength)
    val result = qualifiers(index)
    val randomNum = randomDouble(0,1,2,"rounddown")
    if(randomNum<nullPercentage) "" else qualifiers(index)
  }
}
