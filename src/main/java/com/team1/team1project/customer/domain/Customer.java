package com.team1.team1project.customer.domain;

import com.team1.team1project.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "customers")  // 테이블 이름을 명시적으로 customers로 지정
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;

	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "contact_info")
	private String contactInfo;
	@Column(name = "address")
	private String address;
}
