/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors.messages

/**
  * this message is sent from the RecordsMakerController to the
  * KafkaProducerActor when a file is ready for reading. The msg
  * sent is the full path to the file
  * @param msg - full path to the file
  */
case class KafkaProducerMessage(msg: String){

}
