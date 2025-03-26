package com.team1.team1project.raw_material_suppliers.mapper;

import com.team1.team1project.domain.RawMaterialSupplier;
import com.team1.team1project.raw_material_suppliers.dto.RawMaterialSuppliersDTO;

public class RawMaterialSupplierMapper {

	// Entity -> DTO 변환
	public static RawMaterialSuppliersDTO toDTO(RawMaterialSupplier rawMaterialSuppliers) {
		RawMaterialSuppliersDTO rawMaterialSuppliersDTO = new RawMaterialSuppliersDTO();
		rawMaterialSuppliersDTO.setSupplierId(rawMaterialSuppliers.getSupplierId());
		rawMaterialSuppliersDTO.setSupplierName(rawMaterialSuppliers.getSupplierName());
		rawMaterialSuppliersDTO.setContactInfo(rawMaterialSuppliers.getContactInfo());
		rawMaterialSuppliersDTO.setAddress(rawMaterialSuppliers.getAddress());
		rawMaterialSuppliersDTO.setEmail(rawMaterialSuppliers.getEmail());
		rawMaterialSuppliersDTO.setPhoneNumber(rawMaterialSuppliers.getPhoneNumber());
		return rawMaterialSuppliersDTO;
	}

	// DTO -> Entity 변환
	public static RawMaterialSupplier toEntity(RawMaterialSuppliersDTO rawMaterialSuppliersDTO) {
		RawMaterialSupplier rawMaterialSuppliers = new RawMaterialSupplier();
		rawMaterialSuppliers.setSupplierId(rawMaterialSuppliersDTO.getSupplierId());
		rawMaterialSuppliers.setSupplierName(rawMaterialSuppliersDTO.getSupplierName());
		rawMaterialSuppliers.setContactInfo(rawMaterialSuppliersDTO.getContactInfo());
		rawMaterialSuppliers.setAddress(rawMaterialSuppliers.getAddress());
		rawMaterialSuppliers.setEmail(rawMaterialSuppliers.getEmail());
		rawMaterialSuppliers.setPhoneNumber(rawMaterialSuppliers.getPhoneNumber());

		return rawMaterialSuppliers;
	}
}
