package com.epam.payment;

import com.epam.payment.entity.CustomerAccount;
import com.epam.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

  private final PaymentRepository repository;

  @GetMapping("/get")
  public CustomerAccount get() {
    return repository.findByCustomerId(1L);
  }

}
