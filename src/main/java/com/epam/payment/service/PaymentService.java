package com.epam.payment.service;

import com.epam.payment.dto.OrderDto;

public interface PaymentService {

  boolean enoughMoney(OrderDto order);

}
