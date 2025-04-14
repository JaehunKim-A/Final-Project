package com.team1.team1project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
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
