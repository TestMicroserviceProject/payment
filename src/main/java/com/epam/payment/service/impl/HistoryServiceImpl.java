package com.epam.payment.service.impl;

import com.epam.payment.dto.OrderDto;
import com.epam.payment.dto.RollbackDto;
import com.epam.payment.entity.CustomerAccount;
import com.epam.payment.entity.HistoryEntity;
import com.epam.payment.entity.RollbackHistoryEntity;
import com.epam.payment.repository.HistoryRepository;
import com.epam.payment.repository.PaymentRepository;
import com.epam.payment.repository.RollbackHistoryRepository;
import com.epam.payment.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

  private final HistoryRepository historyRepository;
  private final PaymentRepository paymentRepository;
  private final RollbackHistoryRepository rollbackHistoryRepository;

  @Override
  public HistoryEntity add(OrderDto orderDto) {
    final HistoryEntity historyEntity = new HistoryEntity(orderDto);
    return historyRepository.save(historyEntity);
  }

  @Override
  public void rollback(RollbackDto rollbackDto) {
    final Long customerId = rollbackDto.getClientId();
    final Long orderId = rollbackDto.getOrderId();
    final RollbackHistoryEntity rollbackHistoryEntity = new RollbackHistoryEntity();
    rollbackHistoryEntity.setCustomerId(customerId);
    rollbackHistoryEntity.setOrderId(orderId);
    rollbackHistoryRepository.save(rollbackHistoryEntity);
    final HistoryEntity entity = historyRepository
        .findByCustomerIdAndOrderId(customerId, orderId);
    if (entity != null) {
      historyRepository.delete(entity);
      final CustomerAccount customer = paymentRepository.findByCustomerId(customerId);
      customer.setBalance(customer.getBalance() + entity.getOrderPrice());
    }
  }
}
