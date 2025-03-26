package com.team1.team1project.raw_material_suppliers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RawMaterialSuppliersDTO {

	private int supplierId;
	private String supplierName;  // DB 컬럼명과 맞게 수정
	private String contactInfo;   // DB 컬럼명과 맞게 수정
	private String address;
	private String email;
	private String phoneNumber;

	public RawMaterialSuppliersDTO() {}

	public RawMaterialSuppliersDTO(int supplierId, String supplierName, String contactInfo, String address, String email, String phone_number) {
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.contactInfo = contactInfo;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
}
