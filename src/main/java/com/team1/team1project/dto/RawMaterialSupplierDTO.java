package com.team1.team1project.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

	public String getRegDateFormatted() {
		return regDate != null ? regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "";
	}

	public String getModDateFormatted() {
		return modDate != null ? modDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "";
	}
}
