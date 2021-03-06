/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business

import com.scala.classes.posos.{SimpleMemberAddressWrapper, SimpleMemberRecord}
import com.scala.classes.utilities.{Configuration, LogUtil, PropertyLoader, Randomizer}

/**
  * Singleton Class for creating random SimpleMemberRecord object information
  */
object MMMtableRandomizer extends Randomizer {

  val firstNames = Array("James","John","Robert","Michal","Andy","David","Richard","Charles","Joe","Daniel","Paul","Mark","Mary","Patricia","Elizabeth","Barbara","Jennifer","Maria","Susan","Margaret","Dorothy","Lisa","Karen","Betty","Archie","Brent","Christian","Ashley","Samantha","Rebecca","Frank","Felicia","George","Gertrude","Clarabell","Hunter","Hillary","Ivan","Isabell","Jessica","Lance","Mobey","Marsha","Melissa","Ollie","Nancy","Nigel","Patrick","Patsy","Quincy","Renaldo","Sam","Scooter","Todd","Trevor","William","Wendy","Zack","Chester")
  val middleNames = Array("A.","B.","C.","D.","E.","F.","G.","H.","I.","J.","K.","L.","M.","N.","O.","P.","R.","S.","T.","U.","V.","W.","Y.")
  val lastNames = Array("Smith","Jones","Brown","Johnson","Williams","Miller","Taylor","Wilson","Davis","Macfee","Buckley","Chesterfield","Samuels","Princeton","Dobson","Peabody","Norton","White","Black","Green","Tyson","Clark","Eagerly","Franklin","George","Hilliard","Hilman","Islee","Jackson","Kingsley","Lawson","Lee","Lawrence","Okeefe","Oakley","Zimmerman","Yeardley","Yates","Vance","Underwood","Evers","Dupree","Nunes","Alexander","Applewood","Asher","Ainge","Blackwell","Johnson","Mcdowell","Macdonald")
  val alphaPrefixes = Array("YPVW","YPJW","SHP","WVXT","WVPT","WVPY","J")

  /**
    * initialize
    */
  override def initialize(): Unit = {
    LogUtil.msggenThread2LoggerDEBUG("Member arrays initialized")
  }

  /**
    * generate a random primary policy holder
    * needs a dependent # of 00 always
    * @param ssn = social security # to give to the primary
    * @param accountId = = primary account ID #
    * @return person:SimpleMemberRecord
    */
  def generateRandomPrimaryMember(ssn:String,accountId:String):SimpleMemberRecord = {
    val person:SimpleMemberRecord = new SimpleMemberRecord
    person.accountId=Some(accountId)
    person.subscriberId=Some(accountId.substring(0,9))
    person.socialSecurityNumber=Some(ssn)
    person.dependentNumber=Some(accountId.substring(9,11))
    person.firstName=Some(generateRandomFirstNameForPrimary)
    person.middleName=Some(generateRandomMiddleNameForPrimary())
    person.lastName=Some(generateRandomLastNameForPrimary())
    person.alphaPrefix=Some(generateRandomAlphaPrefix)
    person.gender=Some(generateRandomGender)
    person.dob=Some(generateDateOfBirth)
    person
  }

  /**
    * generate a random spouse
    * needs to have the same last name and address as the primary
    * needs to have the same alpha prefix as the primary
    * needs to have the opposite gender from the primary
    * needs a dependent # of 01 always
    * @param ssn = social security # to give to the spouse
    * @param primary = primary account ID #
    * @return person:SimpleMemberRecord
    */
  def generateRandomSpouse(ssn:String,primary:SimpleMemberAddressWrapper):SimpleMemberRecord = {
    val person:SimpleMemberRecord = new SimpleMemberRecord
    val ageRange:Int = PropertyLoader.getProperty(Configuration.MODE3_SPOUSE_AGERANGE).toInt
    val accountIdStr = primary.simpleMember.accountId.get.substring(0,9)+"01"
    person.accountId=Some(accountIdStr)
    person.subscriberId=Some(accountIdStr.substring(0,9))
    person.socialSecurityNumber=Some(ssn)
    person.dependentNumber=Some(accountIdStr.substring(9,11))
    person.firstName=Some(generateRandomFirstNameForPrimary)
    person.middleName=Some(generateRandomMiddleNameForPrimary())
    person.lastName=(primary.simpleMember.lastName)
    person.alphaPrefix=(primary.simpleMember.alphaPrefix)
    val switchgender = (gender:String) => {if(gender == "F") "M" else "F"}
    person.gender=Some(switchgender(primary.simpleMember.gender.get))
    person.dob=Some(randomDOBForSpouse(primary.simpleMember.dob.get,ageRange))
    person
  }

  /**
    * generate a random dependent
    * needs to have the same last name as the primary
    * needs to have the same alpha prefix as the primary
    * dependent numbers should start with 02 and be consecutive amongst children
    * @param ssn = social security # to give to the dependent
    * @param primary = primary account ID #
    * @param deptNum = dependent # to give to the dependent
    * @return person:SimpleMemberRecord
    */
  def generateRandomDependent(ssn:String,primary:SimpleMemberAddressWrapper,deptNum:Int):SimpleMemberRecord = {
    val person:SimpleMemberRecord = new SimpleMemberRecord
    val deptnumStr:String = deptNumToString(deptNum)
    val accountIdStr = primary.simpleMember.accountId.get.substring(0,9)+deptnumStr
    person.accountId=Some(accountIdStr)
    person.subscriberId=Some(accountIdStr.substring(0,9))
    person.socialSecurityNumber=Some(ssn)
    person.dependentNumber=Some(accountIdStr.substring(9,11))
    person.firstName=Some(generateRandomFirstNameForPrimary)
    person.middleName=Some(generateRandomMiddleNameForPrimary())
    person.lastName=(primary.simpleMember.lastName)
    person.alphaPrefix=(primary.simpleMember.alphaPrefix)
    person.gender=Some(generateRandomGender)
    person.dob=Some(generateDateOfBirthForDependent)
    person
  }
  /**
    * generate a random first name for the primary, spouse, or dependent(child)
    * @return String
    */
  def generateRandomFirstNameForPrimary():String = {
    val firstNamesLength = firstNames.length
    val index = this.randomInteger(0,firstNamesLength)
    firstNames(index)
  }

  /**
    * generate a random middle name for the primary, spouse, or dependent(child)
    * @return String
    */
  def generateRandomMiddleNameForPrimary():String = {
    if(makeBinaryDecision(PropertyLoader.getProperty(Configuration.MODE3_MIDDLENAME_PERCENT).toString.toDouble)) {
      val middleNamesLength = middleNames.length
      val index = this.randomInteger(0,middleNamesLength)
      middleNames(index)
    } else {
      ""
    }
  }

  /**
    * generate a random last name for the primary
    * @return String
    */
  def generateRandomLastNameForPrimary():String = {
    val lastNamesLength = lastNames.length
    val index = this.randomInteger(0,lastNamesLength)
    lastNames(index)
  }

  /**
    * generate a random alpha prefix
    * @return String
    */
  def generateRandomAlphaPrefix():String = {
    val alphaArrayLength = alphaPrefixes.length
    val index = this.randomInteger(0,alphaArrayLength)
    alphaPrefixes(index)
  }

  /**
    * generate a random gender for the primary or dependent(child)
    * @return String
    */
  def generateRandomGender():String = {
    val index = this.randomInteger(0, 2)
    if (index < 1) "M" else "F"
  }

  /**
    * generate a random date of birth for the primary according to certain configuration parameters
    * @return String
    */
  def generateDateOfBirth():String = {
    this.randomDOB(PropertyLoader.getProperty(Configuration.MODE3_PRIMARY_MINAGE).toString.toInt,
      PropertyLoader.getProperty(Configuration.MODE3_PRIMARY_MAXAGE).toString.toInt)
  }

  /**
    * generate a random date of birth for the dependent(child) according to certain configuration parameters
    * @return String
    */
  def generateDateOfBirthForDependent():String = {
    this.randomDOB(0,PropertyLoader.getProperty(Configuration.MODE3_CHILD_MAXAGE).toString.toInt)
  }

  /**
    * pad the dependent number(Int) to a String representation
    * @return String
    */
  def deptNumToString = (deptNum:Int) => {if(deptNum>9) deptNum.toString else "0"+deptNum.toString}
}
