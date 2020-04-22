package com.epam.payment.kafka;

import com.epam.payment.dto.Check;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResultKafkaProducer {

  private final String topic;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  public ResultKafkaProducer(
      @Value("${spring.kafka.producer.topic}") String topic,
      KafkaTemplate<String, String> kafkaTemplate,
      ObjectMapper objectMapper) {
    this.topic = topic;
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
  }

  @SneakyThrows
  public void send(Check check) {
    final String value = objectMapper.writeValueAsString(check);
    kafkaTemplate.send(new ProducerRecord<>(topic, value));
  }
}