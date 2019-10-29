/*
 * Created 2019 by Dylan Kessler
 */

package com.scala.classes.kafka

import java.util.Properties

import com.scala.classes.utilities.{Configuration, PropertyLoader}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

/**
  * this class is a simple Kafka Producer

  */
class SimpleKafkaProducer() {

  /*val conf = ConfigFactory.load
  val envProps = conf.getConfig(args(0))*/
  val topic:String = PropertyLoader.getProperty(Configuration.MODE8_TOPIC_NAME)
  val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, PropertyLoader.getProperty(Configuration.MODE8_BOOTSTRAP_SERVER))
  //props.put(ProducerConfig.CLIENT_ID_CONFIG, "SimpleKafkaProducer")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  val producer = new KafkaProducer[String, String](props)

  def initialize():Unit = {

  }

  def pushMessageToTopic(message:String):Unit = {
    val newRecord:ProducerRecord[String,String] = new ProducerRecord[String,String](topic,message)
    producer.send(newRecord)
    producer.flush()
  }

  def closeProducer():Unit = {
    producer.close()
  }
}
