package com.team1.team1project.customer.domain;

import com.team1.team1project.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
@Table(name = "customers")
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

	// reg_date와 mod_date는 LocalDateTime으로 저장
	@Override
	public LocalDateTime getReg_date() {
		return super.getReg_date();
	}

	@Override
	public LocalDateTime getMod_date() {
		return super.getMod_date();
	}

	// 날짜 포맷팅을 UI에서 사용하기 위해 추가된 메서드
	public String getFormattedRegDate() {
		if (getReg_date() != null) {
			return getReg_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		return null;
	}

	public String getFormattedModDate() {
		if (getMod_date() != null) {
			return getMod_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		return null;
	}
}
