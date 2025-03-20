package com.team1.team1project.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private int customerId;

	@Column(name = "customer_name", nullable = false)
	private String customerName;

	@Column(name = "contact_info")
	private String contactInfo;

	@Column(name = "address")
	private String address;
}
