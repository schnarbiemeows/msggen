/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.actors.messages

/**
  * message sent from the KafkaProducerActor back to the RecordsMakerController
  * when it is done writing a file to a kafka topic
  */
case class KafkaProducerFinishedMessage() {

}
