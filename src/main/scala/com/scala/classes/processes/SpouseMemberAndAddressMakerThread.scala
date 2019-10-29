/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.processes

import com.scala.classes.business.{AddressTableRandomizer, MMMtableRandomizer}
import com.scala.classes.locks.{Counter2Lock, SubscriberIDsForSpousesLock}
import com.scala.classes.posos.{SimpleAddressRecord, SimpleMemberAddressWrapper, SimpleMemberRecord}
import com.scala.classes.utilities.LogUtil

import scala.collection.mutable

/**
  * Thread class for creating Spouses
  * @param subIdSpLock = lock object
  * @param counter1 = lock object with internal counter
  * @param ssnList = list of social security numbers
  * @param subscriberIDsForSpouses = set of subscriber IDs
  * @param finalMap = our final map of primaries, spouses, and dependents
  * @param threadNum = thread number
  * @param recordTotal = number of records to make
  * @param offset = offset to the ssnList
  */
class SpouseMemberAndAddressMakerThread (val subIdSpLock:SubscriberIDsForSpousesLock,
                                         var counter1:Counter2Lock,
                                         var ssnList:List[String],
                                         var subscriberIDsForSpouses:scala.collection.mutable.Stack[Int],
                                         var finalMap:mutable.HashMap[Long, SimpleMemberAddressWrapper],
                                         val threadNum:Int, var recordTotal:Int,val offset:Int) extends Runnable {

  val length:Int = 9
  /**
    * main thread method
    */
  override def run(): Unit = {
    LogUtil.logByNum(s"${Thread.currentThread().getId} : generating Spouse Member Information",threadNum%4)
    //
    while(checkCounter()<recordTotal) {
      val count = getCounterValue()
      LogUtil.logByNum(s"${Thread.currentThread().getId} : counter value: ${count}", threadNum%4)
      if (count >= 0) {
        val ssn = ssnList(offset+count)
        val primarySubId = getPrimaryFromStack()
        val primaryOpt:Option[SimpleMemberAddressWrapper] = finalMap.get(primarySubId)
        var primary:SimpleMemberAddressWrapper = primaryOpt.get
        var smaw: SimpleMemberAddressWrapper = makeWrapperRecord(ssn, primary)
        finalMap.put(smaw.simpleMember.accountId.get.toLong,smaw)
      } else {
        LogUtil.logByNum(s"${Thread.currentThread().getId} : counter value: ${count} already maxxed out", threadNum%4)
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
    LogUtil.logByNum(s"${Thread.currentThread().getId} : getting/incrementing the counter",threadNum%4)
    // critical section
    counter1.synchronized {
      if(counter1.counter<recordTotal) {
        counter1.incrementCounter()
        counter1.counter - 1
      } else {
        -1
      }
    }
  }

  /**
    * critical section method to pick a primary spouse from the subscriberIDsForSpouses Stack
    * @return subscriber Id
    */
  def getPrimaryFromStack():Long = {
    subIdSpLock.synchronized {
      subscriberIDsForSpouses.pop().toLong*100
    }
  }

  /**
    * method to make a SimpleMemberRecord for a Spouse, based off the Primary's record
    * @param ssn = social security # to give the Spouse
    * @param primary = primary object record
    * @return memberRecord
    */
  def makeMemberRecord(ssn:String, primary:SimpleMemberAddressWrapper):SimpleMemberRecord = {
    //LogUtil.logByNum(s"${Thread.currentThread().getId} : making Spouse Member Information record",threadNum%4)
    var memberRecord:SimpleMemberRecord = MMMtableRandomizer.generateRandomSpouse(ssn,primary)
    memberRecord
  }

  /**
    * method to make a SimpleAddressRecord for a Spouse, based off the Primary's record
    * @param primary = primary object record
    * @return addressRecord
    */
  def makeAddressRecord(accountId:String,primary:SimpleMemberAddressWrapper):SimpleAddressRecord = {
    //LogUtil.logByNum(s"${Thread.currentThread().getId} : making Spouse Member Address record",threadNum%4)
    val addressRecord:SimpleAddressRecord = AddressTableRandomizer.generateRandomAddressForSpouse(accountId,primary)
    addressRecord
  }

  /**
    * method to make a SimpleMemberAddressWrapper record for a Spouse, based off the Primary's record
    * this record contains a SimpleMemberRecord and a SimpleAddressRecord
    * @param ssn = social security # to give the Spouse
    * @param primary = primary object record
    * @return smaw
    */
  def makeWrapperRecord(ssn:String, primary:SimpleMemberAddressWrapper):SimpleMemberAddressWrapper = {
    LogUtil.logByNum(s"${Thread.currentThread().getId} : making Spouse Wrapper record",threadNum%4)
    //var smaw:SimpleMemberAddressWrapper = new SimpleMemberAddressWrapper()
    val memberRecord:SimpleMemberRecord = makeMemberRecord(ssn,primary)
    //val addressRecord:Option[SimpleAddressRecord] = makeAddressRecord(memberRecord.get.accountId.get,primary)
    //smaw.simpleMember=(memberRecord)
    //smaw.simpleAddressRecord=(addressRecord)
    SimpleMemberAddressWrapper(memberRecord,makeAddressRecord(memberRecord.accountId.get,primary))
  }

}
