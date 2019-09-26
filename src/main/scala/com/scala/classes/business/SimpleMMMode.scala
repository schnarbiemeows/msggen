/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.business
import java.util.Properties

import com.scala.classes.locks._
import com.scala.classes.posos.SimpleMemberAddressWrapper
import com.scala.classes.processes._
import com.scala.classes.utilities._

import scala.collection.mutable

/**
  * this class controls the workflow for creating Master Member and Member Address
  * data files
  *
  * @param mode - the mode of the program
  * @param properties - singleton Properties object
  */
class SimpleMMMode(val mode: Int, val properties: Properties) extends Mode {
  var ssns:scala.collection.mutable.Set[Int] = null
  var ssnList:List[Int] = null
  var primarySubscriberIDs:scala.collection.mutable.Set[Int] = null
  var subscriberIDsForSpouses:scala.collection.mutable.Stack[Int] = new scala.collection.mutable.Stack[Int]
  var subscriberIDList:List[Int] = null
  var subscriberIdToDependentNumberMap:mutable.HashMap[Int, Int] = new mutable.HashMap[Int,Int]()
  var finalMap:mutable.HashMap[Long, SimpleMemberAddressWrapper] = new mutable.HashMap[Long,SimpleMemberAddressWrapper]()

  private val maxSsnValue = 999999999

  private val maxSubscriberIdValue = 999999999

  /**
    * this method is the workflow for for creating Master Member and Member Address
    * data files
    *
    */
  override def run(): Unit = {
    var runStart = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("inside SimpleMMMode");
    // determine the number of social security numbers to make
    val numToMake:Int = properties.get(Configuration.SSN_NUMBER_TO_MAKE).toString.toInt
    // make the social security numbers
    ssns = makeRandomSSNs(numToMake,0,maxSsnValue)
    ssnList = ssns.toList
    // determine the number of subscriber ID numbers to make
    val numSubsToMake:Int = NumUtility.convertPercentageToInt(numToMake,properties.get(Configuration.PRIMARY_PERCENT).toString.toDouble)
    LogUtil.msggenMasterLoggerDEBUG(s"### of Primaries to make = ${numSubsToMake}");
    // make the subscriber IDs
    primarySubscriberIDs = makeRandomSubscriberIds(numSubsToMake,0,maxSubscriberIdValue)
    subscriberIDList = primarySubscriberIDs.toList
    // make the primary members
    makePrimaries(ssnList,subscriberIDList,subscriberIDsForSpouses,subscriberIdToDependentNumberMap,finalMap,numSubsToMake)
    // determine the number of spouses to make
    val numSpousesToMake:Int = NumUtility.convertPercentageToInt(numToMake,properties.get(Configuration.SPOUCE_PERCENT).toString.toDouble)
    LogUtil.msggenMasterLoggerDEBUG(s"### of Spouses to make = ${numSpousesToMake}");
    var counter:Int = numSubsToMake-1
    LogUtil.msggenMasterLoggerDEBUG(s"### counter offset for the ssn List = ${counter} of ${ssnList.size}");
    LogUtil.msggenMasterLoggerDEBUG(s"size of subscriberIDsForSpouses = ${subscriberIDsForSpouses.size}")
    // make the spouses
    makeSpouses(ssnList, subscriberIDsForSpouses, finalMap, numSpousesToMake,counter)
    counter += numSpousesToMake
    // determine the number of dependents to make
    val numDependentsToMake:Int = NumUtility.convertPercentageToInt(numToMake,properties.get(Configuration.CHILD_PERCENT).toString.toDouble)
    // make the dependents
    makeDependents(ssnList, subscriberIDList, subscriberIdToDependentNumberMap, finalMap, numDependentsToMake,counter)
    // display to the logs the members made
    showMeFinalMap(finalMap)
    LogUtil.msggenMasterLoggerDEBUG("Writing Account IDs to a file")
    // write the account ID #s to a file for the Streaming Messages Mode(StreamingMessagesMode)
    FileIO.outputAccountIdsToFile(finalMap,properties.get(Configuration.SSN_OUTPUT_FILE).toString)
    LogUtil.msggenMasterLoggerDEBUG("DONE - writing Account IDs to a file")
    LogUtil.msggenMasterLoggerDEBUG("Writing Memeber and Address information to files")
    // write the Member and Address information to files
    FileIO.writeMemberAndAddressFiles(finalMap,"JSON",properties.get(Configuration.MM_FILE_LOC).toString,properties.get(Configuration.ADDR_FILE_LOC).toString)
    LogUtil.msggenMasterLoggerDEBUG("DONE - writing Member and Address information to files")
    val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
    LogUtil.logTime(s"SimpleMMMode run() method time = ${runEnd._1} milliseconds")
  }

  /**
    * method to make a Set of random Social Security Numbers
    * @param numberOfSsns = how many SS #s to make
    * @param lower = lower numerical bound for SS# value
    * @param upper = upper bound for SS# value
    * @return ssnSet:scala.collection.mutable.Set[Int]
    */
  def makeRandomSSNs(numberOfSsns: Int, lower: Int = 0, upper: Int = 999999999): scala.collection.mutable.Set[Int] = {
    // TODO - make number of threads variable
    val runStart = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("making random SSN")
    // locks
    val lock:SSNlock = new SSNlock()
    // return set
    var ssnSet:scala.collection.mutable.Set[Int] = scala.collection.mutable.Set[Int]()
    // create 4 runnables
    var run1:SSNMakerThread = new SSNMakerThread(lock,ssnSet,0,numberOfSsns/4)
    var run2:SSNMakerThread = new SSNMakerThread(lock,ssnSet,1,numberOfSsns/4)
    var run3:SSNMakerThread = new SSNMakerThread(lock,ssnSet,2,numberOfSsns/4)
    var run4:SSNMakerThread = new SSNMakerThread(lock,ssnSet,3,numberOfSsns/4)
    // create 4 threads
    var thr1:Thread = new Thread(run1)
    var thr2:Thread = new Thread(run2)
    var thr3:Thread = new Thread(run3)
    var thr4:Thread = new Thread(run4)
    LogUtil.msggenMasterLoggerDEBUG("starting makeRandomSSNs threads")
    // invoke the 4 threads
    thr1.start()
    thr2.start()
    thr3.start()
    thr4.start()
    try {
      // join the 4 threads
      thr1.join()
      thr2.join()
      thr3.join()
      thr4.join()
      LogUtil.msggenMasterLoggerDEBUG("makeRandomSSNs threads joined")
    } catch {
      case e:InterruptedException  => {
        LogUtil.msggenMasterLoggerDEBUG("issue with makeRandomSSNs threads")
        e.printStackTrace()
      }
    }
    LogUtil.msggenMasterLoggerDEBUG(s"Number of generated Social Security Numbers = ${ssnSet.size}");
    LogUtil.msggenMasterLoggerDEBUG("finished making random SSN")
    val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
    LogUtil.logTime(s"makeRandomSSNs() method time = ${runEnd._1} milliseconds")
    ssnSet
  }

  /**
    * method to make a Set of random subscriber IDs
    * @param numberOfSubscriberIds = number of subscriber IDs to make
    * @param lower = lowest possible value for subscriber ID
    * @param upper = highest possible value for subscriber ID
    * @return subscriberIDSet:scala.collection.mutable.Set[Int]
    */
  def makeRandomSubscriberIds(numberOfSubscriberIds: Int,lower: Int = 0, upper: Int = 999999999): scala.collection.mutable.Set[Int] = {
    // TODO - make number of threads variable
    val runStart = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("making random Subscriber IDs")
    // locks
    val lock:SubscriberIDlock = new SubscriberIDlock()
    // return set
    var subscriberIDSet:scala.collection.mutable.Set[Int] = scala.collection.mutable.Set[Int]()
    // create 4 runnables
    var run1:SubscriberIdMakerThread = new SubscriberIdMakerThread(lock,subscriberIDSet,0,numberOfSubscriberIds/4)
    var run2:SubscriberIdMakerThread = new SubscriberIdMakerThread(lock,subscriberIDSet,1,numberOfSubscriberIds/4)
    var run3:SubscriberIdMakerThread = new SubscriberIdMakerThread(lock,subscriberIDSet,2,numberOfSubscriberIds/4)
    var run4:SubscriberIdMakerThread = new SubscriberIdMakerThread(lock,subscriberIDSet,3,numberOfSubscriberIds/4)
    // create 4 threads
    var thr1:Thread = new Thread(run1)
    var thr2:Thread = new Thread(run2)
    var thr3:Thread = new Thread(run3)
    var thr4:Thread = new Thread(run4)
    LogUtil.msggenMasterLoggerDEBUG("starting makeRandomSubscriberIds threads")
    // invoke 4 threads
    thr1.start()
    thr2.start()
    thr3.start()
    thr4.start()
    try {
      // join 4 threads
      thr1.join()
      thr2.join()
      thr3.join()
      thr4.join()
      LogUtil.msggenMasterLoggerDEBUG("makeRandomSubscriberIds threads joined")
    } catch {
      case e:InterruptedException  => {
        LogUtil.msggenMasterLoggerDEBUG("issue with makeRandomSubscriberIds threads")
        e.printStackTrace()
      }
    }
    LogUtil.msggenMasterLoggerDEBUG(s"Number of generated Subscriber IDs = ${subscriberIDSet.size}");
    LogUtil.msggenMasterLoggerDEBUG("finished making random Subscriber IDs")
    val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
    LogUtil.logTime(s"makeRandomSubscriberIds() method time = ${runEnd._1} milliseconds")
    subscriberIDSet
  }

  /**
    * method to generate a number of Primary policy holders with Random member
    * and address information
    * @param ssnList = input list of random SS #s
    * @param subscriberIDList = input list of random subscriber ID #s
    * @param subscriberIDsForSpouses = Set for storing subscriber ID #s for making spouses
    * @param finalMap = our final map of primaries, spouses, and dependents
    * @param numSubsToMake = the number of primary members to make
    */
  def makePrimaries(ssnList:List[Int],
                    subscriberIDList:List[Int],
                    subscriberIDsForSpouses:scala.collection.mutable.Stack[Int],
                    subscriberIdToDependentNumberMap:mutable.HashMap[Int, Int],
                    finalMap:mutable.HashMap[Long, SimpleMemberAddressWrapper],
                    numSubsToMake: Int):Unit = {
    // TODO - make number of threads variable
    val runStart = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("making the Primary Members and Addresses")
    // object locks
    val subIdSpLock:SubscriberIDsForSpousesLock = new SubscriberIDsForSpousesLock()
    val counter:Counter2Lock = new Counter2Lock(0)
    // initialize our member and address randomizer objects
    MMMtableRandomizer.initialize(properties)
    AddressTableRandomizer.initialize(properties)
    // create 4 runnables
    var run1:PrimaryMemberAndAddressMakerThread = new PrimaryMemberAndAddressMakerThread(subIdSpLock, counter,
      ssnList,subscriberIDList,subscriberIDsForSpouses,subscriberIdToDependentNumberMap,finalMap,0,numSubsToMake)
    var run2:PrimaryMemberAndAddressMakerThread = new PrimaryMemberAndAddressMakerThread(subIdSpLock, counter,
      ssnList,subscriberIDList,subscriberIDsForSpouses,subscriberIdToDependentNumberMap,finalMap,1,numSubsToMake)
    var run3:PrimaryMemberAndAddressMakerThread = new PrimaryMemberAndAddressMakerThread(subIdSpLock, counter,
      ssnList,subscriberIDList,subscriberIDsForSpouses,subscriberIdToDependentNumberMap,finalMap,2,numSubsToMake)
    var run4:PrimaryMemberAndAddressMakerThread = new PrimaryMemberAndAddressMakerThread(subIdSpLock, counter,
      ssnList,subscriberIDList,subscriberIDsForSpouses,subscriberIdToDependentNumberMap,finalMap,3,numSubsToMake)
    // create 4 threads
    var thr1:Thread = new Thread(run1)
    var thr2:Thread = new Thread(run2)
    var thr3:Thread = new Thread(run3)
    var thr4:Thread = new Thread(run4)
    LogUtil.msggenMasterLoggerDEBUG("starting makePrimaries threads")
    // invoke 4 threads
    thr1.start()
    thr2.start()
    thr3.start()
    thr4.start()
    try {
      // join 4 threads
      thr1.join()
      thr2.join()
      thr3.join()
      thr4.join()
      LogUtil.msggenMasterLoggerDEBUG("makePrimaries threads joined")
    } catch {
      case e:InterruptedException  => {
        LogUtil.msggenMasterLoggerDEBUG("issue with makePrimaries threads")
        e.printStackTrace()
      }
    }
    LogUtil.msggenMasterLoggerDEBUG(s"Number of Primaries made = ${finalMap.size}");
    LogUtil.msggenMasterLoggerDEBUG("finished making the Primary Members and Addresses")
    val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
    LogUtil.logTime(s"makePrimaries() method time = ${runEnd._1} milliseconds")
  }

  /**
    * method to generate a number of Spouses with Random member
    * and address information, based off of the Primary member that they are married to
    * @param ssnList = input list of random SS #s
    * @param subscriberIDsForSpouses = Set for storing subscriber ID #s for making spouses
    * @param finalMap = our final map of primaries, spouses, and dependents
    * @param numSubsToMake = the number of spouses to make
    * @param counterStart = offset counter indexed into the ssnList
    */
  def makeSpouses(ssnList:List[Int],
                    subscriberIDsForSpouses:scala.collection.mutable.Stack[Int],
                    finalMap:mutable.HashMap[Long, SimpleMemberAddressWrapper],
                    numSubsToMake: Int, counterStart:Int):Unit = {
    // TODO - make number of threads variable
    val runStart = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("making the Spouse Members and Addresses")
    // locks
    val subIdSpLock:SubscriberIDsForSpousesLock = new SubscriberIDsForSpousesLock()
    val counter:Counter2Lock = new Counter2Lock(0)
    // make 4 runnables
    var run1:SpouseMemberAndAddressMakerThread = new SpouseMemberAndAddressMakerThread(subIdSpLock, counter,
      ssnList,subscriberIDsForSpouses,finalMap,0,numSubsToMake,counterStart)
    var run2:SpouseMemberAndAddressMakerThread = new SpouseMemberAndAddressMakerThread(subIdSpLock, counter,
      ssnList,subscriberIDsForSpouses,finalMap,1,numSubsToMake,counterStart)
    var run3:SpouseMemberAndAddressMakerThread = new SpouseMemberAndAddressMakerThread(subIdSpLock, counter,
      ssnList,subscriberIDsForSpouses,finalMap,2,numSubsToMake,counterStart)
    var run4:SpouseMemberAndAddressMakerThread = new SpouseMemberAndAddressMakerThread(subIdSpLock, counter,
      ssnList,subscriberIDsForSpouses,finalMap,3,numSubsToMake,counterStart)
    // make 4 threads
    var thr1:Thread = new Thread(run1)
    var thr2:Thread = new Thread(run2)
    var thr3:Thread = new Thread(run3)
    var thr4:Thread = new Thread(run4)
    LogUtil.msggenMasterLoggerDEBUG("starting makeSpouses threads")
    // start 4 threads
    thr1.start()
    thr2.start()
    thr3.start()
    thr4.start()
    try {
      // join 4 threads
      thr1.join()
      thr2.join()
      thr3.join()
      thr4.join()
      LogUtil.msggenMasterLoggerDEBUG("makeSpouses threads joined")
    } catch {
      case e:InterruptedException  => {
        LogUtil.msggenMasterLoggerDEBUG("issue with makeSpouses threads")
        e.printStackTrace()
      }
    }
    LogUtil.msggenMasterLoggerDEBUG(s"Number of Primaries AND Spouses made = ${finalMap.size}");
    LogUtil.msggenMasterLoggerDEBUG("finished making the Spouse Members and Addresses")
    val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
    LogUtil.logTime(s"makeSpouses() method time = ${runEnd._1} milliseconds")
  }

  /**
    * method to generate a number of Dependents with Random member
    * and address information, based off of the Primary member that they a dependent of
    * @param ssnList = input list of random SS #s
    * @param subscriberIDList = input list of random subscriber ID #s
    * @param subscriberIdToDependentNumberMap = map for storing dependent #s for a given subscriber ID
    * @param finalMap = our final map of primaries, spouses, and dependents
    * @param numSubsToMake = the number of dependents to make
    * @param offset = offset counter indexed into the ssnList
    */
  def makeDependents(ssnList:List[Int],
                     subscriberIDList:List[Int],
                     subscriberIdToDependentNumberMap:mutable.HashMap[Int, Int],
                     finalMap:mutable.HashMap[Long, SimpleMemberAddressWrapper],
                     numSubsToMake: Int, offset:Int):Unit = {
    // TODO - make number of threads variable
    val runStart = DateUtils.nowTime()
    LogUtil.msggenMasterLoggerDEBUG("making the Child Members and Addresses")
    // locks
    val subIdDeptLock:SubscriberIDsForDependentsLock = new SubscriberIDsForDependentsLock()
    val counter:Counter2Lock = new Counter2Lock(0)
    // create 4 runnables
    var run1:DependentMemberAndAddressThread = new DependentMemberAndAddressThread(subIdDeptLock, counter,
      ssnList,subscriberIDList,subscriberIdToDependentNumberMap,finalMap,0,numSubsToMake,offset)
    var run2:DependentMemberAndAddressThread = new DependentMemberAndAddressThread(subIdDeptLock, counter,
      ssnList,subscriberIDList,subscriberIdToDependentNumberMap,finalMap,1,numSubsToMake,offset)
    var run3:DependentMemberAndAddressThread = new DependentMemberAndAddressThread(subIdDeptLock, counter,
      ssnList,subscriberIDList,subscriberIdToDependentNumberMap,finalMap,2,numSubsToMake,offset)
    var run4:DependentMemberAndAddressThread = new DependentMemberAndAddressThread(subIdDeptLock, counter,
      ssnList,subscriberIDList,subscriberIdToDependentNumberMap,finalMap,3,numSubsToMake,offset)
    // create 4 threads
    var thr1:Thread = new Thread(run1)
    var thr2:Thread = new Thread(run2)
    var thr3:Thread = new Thread(run3)
    var thr4:Thread = new Thread(run4)
    LogUtil.msggenMasterLoggerDEBUG("starting makeDependents threads")
    // start 4 threads
    thr1.start()
    thr2.start()
    thr3.start()
    thr4.start()
    try {
      // join 4 threads
      thr1.join()
      thr2.join()
      thr3.join()
      thr4.join()
      LogUtil.msggenMasterLoggerDEBUG("makeDependents threads joined")
    } catch {
      case e:InterruptedException  => {
        LogUtil.msggenMasterLoggerDEBUG("issue with makeDependents threads")
        e.printStackTrace()
      }
    }
    LogUtil.msggenMasterLoggerDEBUG(s"Number of Primaries, Spouses, and Children made = ${finalMap.size}");
    LogUtil.msggenMasterLoggerDEBUG("finished making the Primary Members and Addresses")
    val runEnd = DateUtils.getDifferenceInMilliseconds(runStart)
    LogUtil.logTime(s"makeDependents() method time = ${runEnd._1} milliseconds")
  }

  /**
    * method to display the Dependent # map to the console
    */
  def showMeDependentMap():Unit = {
    println("Keys in the dependent map are:")
    val items:Iterator[Int] = subscriberIdToDependentNumberMap.keysIterator
    while(items.hasNext) {
      val item = items.next()
      println(s" key = ${item} , value = ${subscriberIdToDependentNumberMap.get(item)}")
    }
  }

  /**
    * method to display the final map of members to the console
    * @param finalMap = final map of primaries, spouses, and dependents
    */
  def showMeFinalMap(finalMap: mutable.HashMap[Long, SimpleMemberAddressWrapper]):Unit = {
    val people:Iterator[SimpleMemberAddressWrapper] = finalMap.valuesIterator
    while(people.hasNext) {
      val item:SimpleMemberAddressWrapper = people.next()
      LogUtil.msggenMasterLoggerDEBUG(s"${item.simpleMember.accountId} : name = ${item.simpleMember.lastName}" +
        s", DOB = ${item.simpleMember.dob} , gender = ${item.simpleMember.gender}")
    }
  }
}
