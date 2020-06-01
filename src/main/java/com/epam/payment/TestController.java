package com.epam.payment;

import com.epam.payment.dto.OrderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TestController {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  @GetMapping("/test")
  @SneakyThrows
  public void test() {
    final OrderDto orderDto = new OrderDto(1L, 1L, new ArrayList<>(), "daleko", 3000.0);
    final String value = objectMapper.writeValueAsString(orderDto);
    kafkaTemplate.send("payment.check.request", "Key", value);
  }

}
