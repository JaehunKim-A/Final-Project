package com.team1.team1project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialSupplierDTO {

	private int supplierId;
	private String supplierName;
	private String contactInfo;
	private String address;
	private String email;
	private String phoneNumber;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}
