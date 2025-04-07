package com.team1.team1project.dto;

import lombok.*;

import java.time.LocalDateTime;

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
}
