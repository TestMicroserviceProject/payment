package com.epam.payment.service.impl;

import com.epam.payment.dto.OrderDto;
import com.epam.payment.entity.CustomerAccount;
import com.epam.payment.repository.PaymentRepository;
import com.epam.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository repository;

  @Override
  public boolean enoughMoney(OrderDto order) {
    final CustomerAccount customer = repository.findByCustomerId(order.getCustomerId());
    final int compare = Double.compare(customer.getBalance(), order.getTotal());
    return compare == 1 || compare == 0;
  }

  @Override
  public void update(OrderDto orderDto) {
    final Double orderTotal = orderDto.getTotal();
    final CustomerAccount customer = repository.findByCustomerId(orderDto.getCustomerId());
    customer.setBalance(customer.getBalance() - orderTotal);
    repository.save(customer);
  }

}
