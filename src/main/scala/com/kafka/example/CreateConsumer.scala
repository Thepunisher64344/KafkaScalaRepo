package com.kafka.example


import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer

import java.util.Properties


class CreateConsumer {
  def consume: KafkaConsumer[String, String] = {
    val config = new Properties
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, new StringDeserializer().getClass.getName)
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, new StringDeserializer().getClass.getName)

    //config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")
    //config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    config.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroup")
    config.put(ConsumerConfig.CLIENT_ID_CONFIG, "sampleConsumer")
    val kafkaConsumer = new KafkaConsumer[String, String](config)

    kafkaConsumer
  }
  def consumetodatabase: KafkaConsumer[String, String] = {
    val config2 = new Properties
    config2.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    config2.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, new StringDeserializer().getClass.getName)
    config2.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, new StringDeserializer().getClass.getName)
    config2.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroup2")
    config2.put(ConsumerConfig.CLIENT_ID_CONFIG, "sampleConsumer2")
    val kafkaConsumer2 = new KafkaConsumer[String, String](config2)

    kafkaConsumer2
  }
}
