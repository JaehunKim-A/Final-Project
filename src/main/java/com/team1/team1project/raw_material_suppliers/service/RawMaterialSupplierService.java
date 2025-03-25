package com.team1.team1project.raw_material_suppliers.service;

import com.team1.team1project.domain.RawMaterialSupplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RawMaterialSupplierService {
	List<RawMaterialSupplier> getAllRawMaterialSuppliers();
	Optional<RawMaterialSupplier> getRawMaterialSupplierById(int supplierId);
	RawMaterialSupplier createRawMaterialSupplier(RawMaterialSupplier rawMaterialSupplier);
	RawMaterialSupplier updateRawMaterialSupplier(int supplierId, RawMaterialSupplier rawMaterialSupplier);
	boolean deleteRawMaterialSupplier(int supplierId);

	// ✅ 페이지네이션용 메서드
	Page<RawMaterialSupplier> getRawMaterialSupplierPage(Pageable pageable);
}
