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
public class CustomerDTO {

	private int customerId;
	private String customerName;
	private String contactInfo;
	private String address;
	private LocalDateTime regDate;
	private LocalDateTime modDate;
}
