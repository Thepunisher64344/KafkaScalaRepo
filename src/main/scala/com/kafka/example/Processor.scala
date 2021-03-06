package com.kafka.example

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import org.apache.kafka.clients.consumer.KafkaConsumer

import java.io.ByteArrayOutputStream
import java.time.Duration
import java.util
class Processor{


  val producerExample = new ProducerSend

  val consumerExample = new CreateConsumer
  val kafkaConsumer: KafkaConsumer[String,String] = consumerExample.consume




  def process(): Unit = {

    kafkaConsumer.subscribe(util.Arrays.asList("jsontest"))
    kafkaConsumer.poll(0)
    kafkaConsumer.seekToBeginning(kafkaConsumer.assignment())

    while (true){
      val records = kafkaConsumer.poll(Duration.ZERO)

      records.forEach{ rec =>
        try {

          val objectMapper = new ObjectMapper() with ScalaObjectMapper
          objectMapper.registerModule(DefaultScalaModule)
          val message = rec.value()

          val sum: Sum = objectMapper.readValue[Sum](message)
          println(sum.getA())
          println(sum.getB())

          val out = new ByteArrayOutputStream()


          sum.setSum(sum.getA() + sum.getB())
          objectMapper.writeValue(out, sum)

          val json = out.toString

          producerExample.produce("jsontest1", json)
//          val avg = (sum.getA()+sum.getB()+sum.getSum())/3
//          ConnectDb.sendData(sum.getA(),sum.getB(),sum.getSum(), avg)
        }
        catch{
          case e:JsonProcessingException => throw e
        }


      }


    }


  }
}