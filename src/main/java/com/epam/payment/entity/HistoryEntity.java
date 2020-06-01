package com.epam.payment.entity;

import com.epam.payment.dto.OrderDto;
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
@Table(name = "customer_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_history_seq")
  @SequenceGenerator(name = "customer_history_seq", sequenceName = "customer_history_id_seq", allocationSize = 1)
  private Long id;
  private Long customerId;
  private Long orderId;
  private Double orderPrice;

  public HistoryEntity(OrderDto orderDto) {
    this.customerId = orderDto.getCustomerId();
    this.orderId = orderDto.getOrderId();
    this.orderPrice = orderDto.getTotal();
  }
}
