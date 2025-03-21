package com.team1.team1project.customer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

	private int customerId;
	private String customerName;  // DB 컬럼명과 맞게 수정
	private String contactInfo;   // DB 컬럼명과 맞게 수정
	private String address;

	public CustomerDTO() {}

	public CustomerDTO(int customerId, String customerName, String contactInfo, String address) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.contactInfo = contactInfo;
		this.address = address;
	}
}
