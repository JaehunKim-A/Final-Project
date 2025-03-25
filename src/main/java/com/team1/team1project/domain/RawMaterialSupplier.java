package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
@Table(name = "raw_material_suppliers")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RawMaterialSupplier extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int supplierId;

	@Column(name = "supplier_name")
	private String supplierName;

	@Column(name = "contact_info")
	private String contactInfo;

	@Column(name = "address")
	private String address;

	@Column(name = "email")
	private String email;

	@Column(name = "phone_number")
	private String phone_number;

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
