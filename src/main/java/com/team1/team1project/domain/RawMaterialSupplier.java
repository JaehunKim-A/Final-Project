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
	private String phoneNumber;

	// reg_date와 mod_date는 LocalDateTime으로 저장
	@Override
	public LocalDateTime getRegDate() {
		return super.getRegDate();
	}

	@Override
	public LocalDateTime getModDate() {
		return super.getModDate();
	}

	// 날짜 포맷팅을 UI에서 사용하기 위해 추가된 메서드
	public String getFormattedRegDate() {
		if (getRegDate() != null) {
			return getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		return null;
	}

	public String getFormattedModDate() {
		if (getModDate() != null) {
			return getModDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		return null;
	}
}
