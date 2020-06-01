package com.epam.payment.service;

import com.epam.payment.dto.OrderDto;
import com.epam.payment.dto.RollbackDto;
import com.epam.payment.entity.HistoryEntity;

public interface HistoryService {

  HistoryEntity add(OrderDto orderDto);

  void rollback(RollbackDto rollbackDto);

}
