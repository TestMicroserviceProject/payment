package com.epam.payment.kafka;

import com.epam.payment.dto.OrderDto;
import com.epam.payment.dto.ResultDto;
import com.epam.payment.dto.ResultDto.Check;
import com.epam.payment.dto.ResultDto.Service;
import com.epam.payment.dto.RollbackDto;
import com.epam.payment.entity.RollbackHistoryEntity;
import com.epam.payment.repository.RollbackHistoryRepository;
import com.epam.payment.service.HistoryService;
import com.epam.payment.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

  private final ObjectMapper objectMapper;
  private final PaymentService paymentService;
  private final ResultKafkaProducer kafkaProducer;
  private final HistoryService historyService;
  private final RollbackHistoryRepository rollbackHistoryRepository;

  @SneakyThrows
  @KafkaListener(
      topics = "${spring.kafka.consumer.topic}",
      clientIdPrefix = "payment-request-client",
      groupId = "payment-request-group",
      containerFactory = "consumerFactory"
  )
  @Transactional
  public ResultDto consume(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
    final OrderDto orderDto = objectMapper.readValue(record.value(), OrderDto.class);
    final Long customerId = orderDto.getCustomerId();
    final Long orderId = orderDto.getOrderId();
    final RollbackHistoryEntity rollback = rollbackHistoryRepository
        .findByCustomerIdAndOrderId(customerId, orderId);
    ResultDto resultDto;
    if (rollback == null) {
      final boolean enough = paymentService.enoughMoney(orderDto);
      if (enough) {
        resultDto = new ResultDto(orderDto, Check.SUCCESS, Service.PAYMENT);
        paymentService.update(orderDto);
        historyService.add(orderDto);
      } else {
        resultDto = new ResultDto(orderDto, Check.FAIL, Service.PAYMENT);
      }
      kafkaProducer.send(resultDto);
    }else {
      resultDto = new ResultDto(orderDto, Check.CANCELLED, Service.PAYMENT);
    }
    acknowledgment.acknowledge();
    return resultDto;
  }

  @SneakyThrows
  @KafkaListener(
      topics = "${spring.kafka.consumer.rollback.topic}",
      clientIdPrefix = "payment-rollback-client",
      groupId = "payment-rollback-group",
      containerFactory = "consumerFactory"
  )
  @Transactional
  public void rollback(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
    final RollbackDto rollbackDto = objectMapper.readValue(record.value(), RollbackDto.class);
    historyService.rollback(rollbackDto);
    acknowledgment.acknowledge();
  }
}
