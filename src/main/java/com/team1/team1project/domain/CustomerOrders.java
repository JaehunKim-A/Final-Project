package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "customer_orders")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerOrders extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;

	@Column(name = "customer_id")
	private int customerId;

	@Column(name = "order_date")
	private LocalDateTime orderDate;

	@Column(name = "status", length = 50)
	private String status;

	@Column(name = "total_amount", precision = 10, scale = 2)
	private BigDecimal totalAmount;
}

