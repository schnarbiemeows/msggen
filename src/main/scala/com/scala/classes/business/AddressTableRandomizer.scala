/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import com.scala.classes.posos.{SimpleAddress, SimpleAddressRecord, SimpleMemberAddressWrapper}
import com.scala.classes.utilities.{Configuration, LogUtil, PropertyLoader, Randomizer}

/**
  * Singleton Class for creating random SimpleAddress object information
  */
object AddressTableRandomizer extends Randomizer {

  /**
    * addressLine1, part 1
    */
  val randomStreets1 = Array("Apple Orchard","Lucky Pass","State","Laramy","Apple Blossom","Acadia","Winding","Snow Crest","Parkwood","Martin Luther King","Wall","Riverbend","Country","St. Charles","St. Abbey","Blatham")
  /**
    * addressLine1, part 2
    */
  val randomStreets2 = Array("Lane","Street","Road","Avenue","Way","Circle","Trail","Drive","Boulevard")
  /**
    *
    */
  val randomAddressLine2 = Array("Apt. 12","Suite 4","P.O. Box 12345","Upper Level")
  /**
    * list of random cities for each state
    */
  val alabamaCities = Array("Birmingham","Montgomery","Mobile")
  val alaskaCities = Array("Nome","Anchorage","Juneau","Eagle","Circle")
  val arizonaCities = Array("Pheonix","Tuscon")
  val arkansasCities = Array("Little Rock","Fayetteville")
  val californiaCities = Array("Los Angeles","San Fransisco","Sacramento","San Diego","Oakland","Riverside")
  val colorodoCities = Array("Denver","Colorodo Springs","Boulder")
  val connecticutCities = Array("Hartford","New Haven")
  val delawareCities = Array("Dover","Newark","Wilmington")
  val floridaCities = Array("Miami","Orlando","Tampa Bay","Boca Raton","Jacksonville")
  val georgiaCities = Array("Atlanta","Savannah","Augusta")
  val hawaiiCities = Array("Hilo","Honolulu")
  val idahoCities = Array("Boise","Idaho Falls")
  val illinoisCities = Array("Chicago","Springfield","Peoria")
  val indianaCities = Array("Indianapolis","Fort Wayne","Bloomington")
  val iowaCities = Array("Des Moines","Iowa City")
  val kansasCities = Array("Wichita","Kansas City")
  val kentuckyCities = Array("Louisville","Lexington")
  val louisianaCities = Array("New Orleans","Baton Rouge")
  val maineCities = Array("Portland","Bangor")
  val marylandCities = Array("Baltimore","Annapolis","Ocean City","Silver Spring")
  val massachusettsCities = Array("Boston","Worchester","Salem","Newton","Lowell")
  val michiganCities = Array("Detroit","Lansing","East Lansing","Kalamazoo")
  val minnesotaCities = Array("minneapolis","Saint Paul","Duluth")
  val mississippiCities = Array("Jackson","Biloxi")
  val missouriCities = Array("St. Louis","Kansas City","Springfield")
  val montanaCities = Array("Butte","Missoula")
  val nebraskaCities = Array("Lincoln","Omaha")
  val nevadaCities = Array("Las Vegas","Reno","Caron City")
  val newHampshireCities = Array("Portsmouth","Concord")
  val newJerseyCities = Array("Newark","Jersey City","Hoboken","Trenton","Princeton")
  val newMexicoCities = Array("Albuquerque","Santa Fe")
  val newYorkCities = Array("NYC","Brooklyn","Albany","Buffalo","Queens")
  val northCarolinaCities = Array("Raleigh","Charlotte","Durham","Greensboro","Chapel Hill")
  val northDakotaCities = Array("Fargo","Bismark")
  val ohioCities = Array("Cleveland","Cincinnati","Columbus","Dayton")
  val oklahomaCities = Array("Oklahoma Cty","Tulsa")
  val oregonCities = Array("Portland","Eugene","Salem")
  val pennsylvaniaCities = Array("Philadelphia","Pittsburg","Harrisburg","Lancaster")
  val rhodeIslandCities = Array("Providence","Newport")
  val southCarolinaCities = Array("Charleston","Columbia")
  val southDakotaCities = Array("SiouxFalls","Rapid City")
  val tennesseeCities = Array("Nashville","Memphis","Gatlinburg","Knoxville")
  val texasCities = Array("Austin","Dallas","San Antonio","Houston","Fort Worth")
  val utahCities = Array("Salt Lake City","Provo","Ogden")
  val vermontCities = Array("Burlington","Montpelier")
  val virginiaCities = Array("Richmond","Norfolk","Alexandria","Charlottesville","Blacksburg")
  val washingtonCities = Array("Seattle","Takoma","Sokane","Olympia","Redmond")
  val westVirginiaCities = Array("Charleston","Huntington","Clarksburg")
  val wisconsinCities = Array("Madison","Green Bay","Milwaukee")
  val wyomingCities = Array("Casper","Cheyenne","Jackson")

  /**
    * map of each state -> city list
    */
  val usStates = Map("AL" -> alabamaCities,"AK" -> alaskaCities,"AZ" -> arizonaCities,
    "AR" -> arkansasCities,"CA" -> californiaCities, "CO" -> colorodoCities,"CT" -> connecticutCities,
    "DE" -> delawareCities,"FL" -> floridaCities, "GA" -> georgiaCities,"HI" -> hawaiiCities,
    "ID" -> idahoCities,"IL" -> illinoisCities, "IN" -> indianaCities,"IA" -> iowaCities,
    "KS" -> kansasCities,"KY" -> kentuckyCities,"LA" -> louisianaCities,"ME" -> maineCities,
    "MD" -> marylandCities,"MA" -> massachusettsCities,"MI" -> michiganCities,"MN" -> minnesotaCities,
    "MS" -> mississippiCities,"MO" -> missouriCities,"MT" -> montanaCities,"NE" -> nebraskaCities,
    "NV" -> nevadaCities,"NH" -> newHampshireCities,"NJ" -> newJerseyCities,"NM" -> newMexicoCities,
    "NY" -> newYorkCities,"NC" -> northCarolinaCities,"ND" -> northDakotaCities,"OH" -> ohioCities,
    "OK" -> oklahomaCities,"OR" -> oregonCities,"PA" -> pennsylvaniaCities,"RI" -> rhodeIslandCities,
    "SC" -> southCarolinaCities,"SD" -> southDakotaCities,"TN" -> tennesseeCities,"TX" -> texasCities,
    "UT" -> utahCities,"VT" -> vermontCities,"VA" -> virginiaCities,"WA" -> washingtonCities,
    "WV" -> westVirginiaCities,"WI" -> wisconsinCities,"WY" -> wyomingCities)



  /**
    * initialize
    */
  override def initialize(): Unit = {
    LogUtil.msggenThread2LoggerDEBUG("Address Arrays initialized")
  }

  /**
    * generate a random address for the primary or dependent(child)
    * random chance of having a second line to the address
    * @param accountId = primary account ID #
    * @return record:SimpleAddressRecord
    */
  def generateRandomAddressForPrimary(accountId: String): SimpleAddressRecord = {
    val address:SimpleAddress = new SimpleAddress
    address.addressLine1=Some(generateRandomStreetAddress1())
    if(makeBinaryDecision(PropertyLoader.getProperty(Configuration.MODE3_LINE2_ADDR_PERCENT).toString.toDouble)) {
      address.addressLine2=Some(generateRandomStreetAddress2())
    }
    address.stateCode=Some(generateRandomState())
    address.city=(generateRandomCity(address.stateCode.get))
    address.zip5=Some(padZipCode(generateRandomZipCode()))
    SimpleAddressRecord(accountId,address)
  }

  /**
    * generate a random address for the spouse(same as the primary's)
    * @param accountId = primary account ID #
    * @param primary = primary object
    * @return record:SimpleAddressRecord
    */
  def generateRandomAddressForSpouse(accountId: String, primary:SimpleMemberAddressWrapper): SimpleAddressRecord = {
    SimpleAddressRecord(accountId,primary.simpleAddressRecord.address)
  }

  /**
    * generate a random address for the dependent(child)
    * random chance they can have a different address than the primary, otherwise
    * they should have the same address
    * @param accountId = primary account ID #
    * @param primary = primary object
    * @return record:SimpleAddressRecord
    */
  def generateRandomAddressForDependent(accountId: String, primary:SimpleMemberAddressWrapper): SimpleAddressRecord = {
    val chance:Double = PropertyLoader.getProperty(Configuration.MODE3_NUMBER_OF_DEPENDENTS).toDouble
    if(makeBinaryDecision(chance)) {
      LogUtil.msggenMasterLoggerDEBUG(s"generating a different address for a dependent ${accountId}");
      generateRandomAddressForPrimary(accountId)
    } else {
      SimpleAddressRecord(accountId,primary.simpleAddressRecord.address)
    }
  }

  /**
    * generate a random street address 1
    * @return String
    */
  def generateRandomStreetAddress1(): String = {
    val addr1length = randomStreets1.length
    val addr2length = randomStreets2.length
    val index1 = this.randomInteger(0,addr1length)
    val index2 = this.randomInteger(0,addr2length)
    randomStreets1(index1)+" "+randomStreets2(index2)
  }

  /**
    * generate a random street address 1
    * @return String
    */
  def generateRandomStreetAddress2(): String = {
    val addr2length = randomAddressLine2.length
    val index2 = this.randomInteger(0,addr2length)
    randomAddressLine2(index2)
  }

  /**
    * generate a random state
    * @return String
    */
  def generateRandomState(): String = {
    val stateCodes = usStates.keys.toList
    val stateCodesLength = stateCodes.length
    val index = this.randomInteger(0,stateCodesLength)
    stateCodes(index)
  }

  /**
    * generate a random city for a given state
    * @param stateCode = state code to generate a city for
    * @return String
    */
  def generateRandomCity(stateCode: String): Option[String] = {
    val stateCitiesOption:Option[Array[String]] = usStates.get(stateCode)
    if(stateCitiesOption!=None) {
      val stateCities = stateCitiesOption.get
      val stateCitieslength = stateCities.size
      val index = this.randomInteger(0,stateCitieslength)
      Some(stateCities(index))
    } else {
      None
    }
  }

  /**
    * generate a random 5-digit zip code
    * @return Int
    */
  def generateRandomZipCode(): Int = {
    this.randomInteger(0,99999)
  }

  /**
    * pad a 5-digit zip code(if needed) and convert to String
    * @param zip = zip to pad
    * @return String
    */
  def padZipCode(zip: Int): String = {
    val length = zip.toString.length
    if(length==5) {
      zip.toString
    }
    var zipStr:String = zip.toString
    for(count <- 0 until 5-length) {
      zipStr = "0".concat(zipStr)
    }
    zipStr
  }
}
