package com.epam.payment.repository;

import com.epam.payment.entity.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<CustomerAccount, Long> {

  CustomerAccount findByCustomerId(Long customerId);

}
