package com.team1.team1project.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private int customerId;
	private String customerName;
	private String contactInfo;
	private String address;
	private LocalDateTime regDate;
	private LocalDateTime modDate;

	public String getRegDateFormatted() {
		return regDate != null ? regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "";
	}

	public String getModDateFormatted() {
		return modDate != null ? modDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "";
	}
}