package com.team1.team1project.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrdersDTO {
	private int orderId;
	private int customerId;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime orderDate;
	private String status;
	private BigDecimal totalAmount;
	private LocalDateTime regDate;
	private LocalDateTime modDate;

	public String getOrderDateFormatted() {
		return orderDate != null ? orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "";
	}

	public String getRegDateFormatted() {
		return regDate != null ? regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "";
	}

	public String getModDateFormatted() {
		return modDate != null ? modDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "";
	}

}
