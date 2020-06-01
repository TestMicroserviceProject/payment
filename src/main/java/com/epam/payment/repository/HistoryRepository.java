package com.epam.payment.repository;

import com.epam.payment.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {

  HistoryEntity findByCustomerIdAndOrderId(long customerId, long orderId);

}
