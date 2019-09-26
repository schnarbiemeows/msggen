/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.processes

import com.scala.classes.business.{AddressTableRandomizer, MMMtableRandomizer}
import com.scala.classes.locks.{Counter2Lock, SubscriberIDsForSpousesLock}
import com.scala.classes.posos.{SimpleAddressRecord, SimpleMemberAddressWrapper, SimpleMemberRecord}
import com.scala.classes.utilities.{LogUtil, NumUtility}

import scala.collection.mutable

/**
  * thread class for creating Primary policy holders
  * @param subIdSpLock = lock object
  * @param counter1 = lock object with internal counter
  * @param ssnList = list of social security numbers
  * @param subscriberIdList = list of subscriber IDs
  * @param subscriberIDsForSpouses = set of subscriber IDs
  * @param subscriberIdToDependentNumberMap = map of subscriber ID # to dependent #
  * @param finalMap = our final map of primaries, spouses, and dependents
  * @param threadNum = thread number
  * @param recordTotal = number of records to make
  */
class PrimaryMemberAndAddressMakerThread(val subIdSpLock:SubscriberIDsForSpousesLock,
                                         var counter1:Counter2Lock,
                                         var ssnList:List[Int],
                                         var subscriberIdList:List[Int],
                                         var subscriberIDsForSpouses:scala.collection.mutable.Stack[Int],
                                         var subscriberIdToDependentNumberMap:mutable.HashMap[Int, Int],
                                         var finalMap:mutable.HashMap[Long, SimpleMemberAddressWrapper],
                                         val threadNum:Int, var recordTotal:Int) extends Runnable {

  val length:Int = 9
  /**
    * main thread method
    */
  override def run(): Unit = {
    //LogUtil.logByNum(s"${Thread.currentThread().getId} : logNum = ${logNum} , logNum%4 = ${logNum%4}",threadNum%4)
    LogUtil.logByNum(s"${Thread.currentThread().getId} : generating Primary Member Information",threadNum%4)
    while(checkCounter()<recordTotal) {
      val count = getCounterValue()
      LogUtil.logByNum(s"${Thread.currentThread().getId} : counter value: " + count, threadNum%4)
      if (count >= 0) {
        val ssn = ssnList(count)
        val subId = subscriberIdList(count)
        var smaw: SimpleMemberAddressWrapper = makeWrapperRecord(ssn, subId)
        val accountId:Long = subId.toLong*100
        LogUtil.msggenMasterLoggerDEBUG(s"${Thread.currentThread().getId} : putting -> ${accountId} into MAP for counter = ${count}")
        finalMap.put(accountId,smaw)
        addToSpouseList(subId)
        addToDependentList(subId)
      } else {
        LogUtil.logByNum(s"${Thread.currentThread().getId} : counter value: " + count + " already maxxed out", threadNum%4)
      }
    }
  }

  /**
    * critical section method to check the record counter
    * @return the counter's value
    */
  def checkCounter():Int = {
    //LogUtil.logByNum(s"${Thread.currentThread().getId} : checking the counter",threadNum%4)
    // critical section
    counter1.synchronized {
      counter1.counter
    }
  }

  /**
    * critical section method to check-then-increment the record counter
    * @return the counter's value
    */
  def getCounterValue():Int = {
    // critical section
    counter1.synchronized {
      if(counter1.counter<recordTotal) {
        LogUtil.logByNum(s"${Thread.currentThread().getId} : incrementing the counter",threadNum%4)
        counter1.incrementCounter()
        counter1.counter -1
      } else {
        -1
      }
    }
  }

  /**
    * critical section method to add a subscriber ID to the subscriberIDsForSpouses Stack
    * @param subId - subscriber ID of the primary member
    */
  def addToSpouseList(subId:Int):Unit ={
    // critical section
    subIdSpLock.synchronized {
      subscriberIDsForSpouses.push(subId)
      LogUtil.logByNum(s"${Thread.currentThread().getId} : size of subscriberIDsForSpouses = ${subscriberIDsForSpouses.size}",threadNum%4)
    }
  }

  /**
    * method to map the (Primary's subscriber ID, 2) to the subscriberIdToDependentNumberMap
    * @param subId - subscriber ID of the primary member
    */
  def addToDependentList(subId:Int):Unit = {
    subscriberIdToDependentNumberMap.put(subId,2)
  }
    /**
      * method to make a SimpleMemberRecord for a Primary Member
      * @param ssn = social security # to give the primary
      * @param subId = subscriber ID # to give the primary
      * @return memberRecord
      */
  def makeMemberRecord(ssn:String, subId:String):SimpleMemberRecord = {
    //LogUtil.logByNum(s"${Thread.currentThread().getId} : making Primary Member Information record",threadNum%4)
    var memberRecord:SimpleMemberRecord = MMMtableRandomizer.generateRandomPrimaryMember(ssn,subId)
    memberRecord
  }

    /**
      * method to make a SimpleAddressRecord for a Primary Member
      * @param subId = subscriber ID # to give the primary
      * @return addressRecord
      */
  def makeAddressRecord(subId:String):SimpleAddressRecord = {
    //LogUtil.logByNum(s"${Thread.currentThread().getId} : making Primary Member Address record",threadNum%4)
    val addressRecord:SimpleAddressRecord = AddressTableRandomizer.generateRandomAddressForPrimary(subId)
    addressRecord
  }

    /**
      * method to make a SimpleMemberAddressWrapper record for a Primary Member,
      * which contains a SimpleMemberRecord and a SimpleAddressRecord
      * @param ssn = social security # to give the primary
      * @param subId = subscriber ID # to give the primary
      * @return smaw
      */
  def makeWrapperRecord(ssn:Int, subId:Int):SimpleMemberAddressWrapper = {
    var smaw:SimpleMemberAddressWrapper = new SimpleMemberAddressWrapper()
    val ssnStr = NumUtility.padIntToString(ssn,length)
    val accountIdStr = NumUtility.padIntToString(subId,length)+"00"
    LogUtil.logByNum(s"${Thread.currentThread().getId} : making Primary Wrapper record for ssn = ${ssnStr} , account ID = ${accountIdStr} ",threadNum%4)
    val memberRecord:SimpleMemberRecord = makeMemberRecord(ssnStr,accountIdStr)
    val addressRecord:SimpleAddressRecord = makeAddressRecord(accountIdStr)
    smaw.simpleMember=(memberRecord)
    smaw.simpleAddressRecord=(addressRecord)
    smaw
  }

}
