package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;

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


}
