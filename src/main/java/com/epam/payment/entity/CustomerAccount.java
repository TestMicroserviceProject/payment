package com.epam.payment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_accounts_seq")
  @SequenceGenerator(name = "customer_accounts_seq", sequenceName = "customer_accounts_id_seq", allocationSize = 1)
  private Long customerId;

  @Column
  private Double balance;
}
