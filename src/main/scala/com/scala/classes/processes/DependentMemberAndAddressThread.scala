/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.processes

import com.scala.classes.business.{AddressTableRandomizer, MMMtableRandomizer}
import com.scala.classes.locks.{Counter2Lock, SubscriberIDsForDependentsLock}
import com.scala.classes.posos.{SimpleAddressRecord, SimpleMemberAddressWrapper, SimpleMemberRecord}
import com.scala.classes.utilities.{LogUtil, NumUtility}

import scala.collection.mutable

/**
  * thread class for creating dependents(children)
  * @param subIdDeptLock = lock object
  * @param counter1 = lock object with internal counter
  * @param ssnList = list of social security numbers
  * @param subscriberIdList = list of subscriber IDs
  * @param subscriberIdToDependentNumberMap = map of subscriber ID # to dependent #
  * @param finalMap = our final map of primaries, spouses, and dependents
  * @param threadNum = thread number
  * @param recordTotal = number of records to make
  * @param offset = offset to the ssnList
  */
class DependentMemberAndAddressThread(val subIdDeptLock:SubscriberIDsForDependentsLock,
                                      val counter1:Counter2Lock,
                                      var ssnList:List[String],
                                      var subscriberIdList:List[Int],
                                      var subscriberIdToDependentNumberMap:mutable.HashMap[Int, Int],
                                      var finalMap:mutable.HashMap[Long, SimpleMemberAddressWrapper],
                                      val threadNum:Int, var recordTotal:Int,val offset:Int) extends Runnable {

  val length:Int = 9
  /**
    * main thread method
    */
  override def run(): Unit = {
    LogUtil.logByNum(s"${Thread.currentThread().getId} : generating Dependent Member Information",threadNum%4)
    while(checkCounter()<recordTotal) {
      val count = getCounterValue()
      LogUtil.logByNum(s"${Thread.currentThread().getId} : dependent counter value: ${count}, recordTotal: ${recordTotal}, offset = ${offset}", threadNum%4)
      if (count >= 0) {
        val ssn = ssnList(offset+count)
        val primarySubId = getPrimaryFromList()
        //println(s"searching dependent map with sub ID = ${primarySubId}")
        val deptNum = getDependentNumValue(primarySubId)
        val primaryOpt:Option[SimpleMemberAddressWrapper] = finalMap.get(primarySubId.toLong*100)
        if(primaryOpt==None) println(s"nothing here for sub ID = ${primarySubId}")
        var primary:SimpleMemberAddressWrapper = primaryOpt.get
        val smaw:SimpleMemberAddressWrapper = makeWrapperRecord(ssn, primary,deptNum)
        val accountId:Long = smaw.simpleMember.accountId.get.toLong
        LogUtil.msggenMasterLoggerDEBUG(s"${Thread.currentThread().getId} : putting -> ${accountId} into MAP for counter = ${count}")
        finalMap.put(accountId,smaw)
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
    LogUtil.logByNum(s"${Thread.currentThread().getId} : checking the dependent counter",threadNum%4)
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
    LogUtil.logByNum(s"${Thread.currentThread().getId} : incrementing the dependent counter",threadNum%4)
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
    * method to retrieve a random Primary's subscriber Id from the subscriberIdList
    * @return Int
    */
  def getPrimaryFromList():Int = {
    val listSize:Int = subscriberIdList.size
    val index:Int = NumUtility.randomInteger(0,listSize)
    subscriberIdList(index)
  }

  /**
    * critical section to retrieve AND increment the dependent # value in the subscriberIdToDependentNumberMap
    * @param primarySubId - subscriber ID number of the primary member
    * @return num
    */
  def getDependentNumValue(primarySubId:Int):Int = {
    // critical section
    subIdDeptLock.synchronized {
      val num:Int = subscriberIdToDependentNumberMap.getOrElse(primarySubId,0)+1
      subscriberIdToDependentNumberMap.put(primarySubId, num)
      num
    }
  }
  /**
    * method to make a SimpleMemberRecord for a Dependent, based off the Primary's record
    * @param ssn - social security number of the primary member
    * @param primary - primary member Object
    * @return memberRecord
    */
  def makeMemberRecord(ssn:String, primary:SimpleMemberAddressWrapper,deptNum:Int):SimpleMemberRecord = {
    LogUtil.logByNum(s"${Thread.currentThread().getId} : making Dependent Member Information record",threadNum%4)
    val memberRecord:SimpleMemberRecord = MMMtableRandomizer.generateRandomDependent(ssn,primary,deptNum)
    memberRecord
  }

  /**
    * method to make a SimpleAddressRecord for a Dependent, based off the Primary's record
    * @param primary - primary member Object
    * @return addressRecord
    */
  def makeAddressRecord(accountId:String,primary:SimpleMemberAddressWrapper):SimpleAddressRecord = {
    LogUtil.logByNum(s"${Thread.currentThread().getId} : making Dependent Member Address record",threadNum%4)
    val addressRecord:SimpleAddressRecord = AddressTableRandomizer.generateRandomAddressForDependent(accountId,primary)
    addressRecord
  }

  /**
    * method to make a SimpleMemberAddressWrapper record for a Primary Member, based off the Primary's record
    * which contains a SimpleMemberRecord and a SimpleAddressRecord
    * @param ssn - social security number of the primary member
    * @param primary - primary member Object
    * @return smaw
    */
  def makeWrapperRecord(ssn:String, primary:SimpleMemberAddressWrapper,deptNum:Int):SimpleMemberAddressWrapper = {
    LogUtil.logByNum(s"${Thread.currentThread().getId} : making Dependent Wrapper record",threadNum%4)
    val memberRecord:SimpleMemberRecord = makeMemberRecord(ssn,primary,deptNum)
    SimpleMemberAddressWrapper(memberRecord,makeAddressRecord(memberRecord.accountId.get,primary))
  }

}
