package com.epam.payment.entity;

import java.io.Serializable;
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
@Table(name = "payment_rollback_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RollbackHistoryEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_rollback_history_seq")
  @SequenceGenerator(name = "payment_rollback_history_seq", sequenceName = "payment_rollback_history_id_seq", allocationSize = 1)
  private Long id;
  private Long customerId;
  private Long orderId;
}
