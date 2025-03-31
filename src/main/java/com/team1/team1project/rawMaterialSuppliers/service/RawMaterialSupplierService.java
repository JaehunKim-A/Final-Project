package com.team1.team1project.rawMaterialSuppliers.service;

import com.team1.team1project.dto.RawMaterialSupplierDTO;

import java.util.List;
import java.util.Optional;

public interface RawMaterialSupplierService {
	List<RawMaterialSupplierDTO> getAllRawMaterialSuppliers();
	Optional<RawMaterialSupplierDTO> getRawMaterialSupplierById(int supplierId);
	RawMaterialSupplierDTO createRawMaterialSupplier(RawMaterialSupplierDTO rawMaterialSupplierDTO);
	RawMaterialSupplierDTO updateRawMaterialSupplier(int supplierId, RawMaterialSupplierDTO rawMaterialSupplierDTO);
	boolean deleteRawMaterialSupplier(int supplierId);
	RawMaterialSupplierDTO getRawMaterialSupplierByName(String supplierName);
	void updateRawMaterialSupplier(Integer supplierId, RawMaterialSupplierDTO rawMaterialSupplierDTO);


}
