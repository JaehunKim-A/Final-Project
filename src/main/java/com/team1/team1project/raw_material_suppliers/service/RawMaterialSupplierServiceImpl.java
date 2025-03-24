package com.team1.team1project.raw_material_suppliers.service;

import com.team1.team1project.raw_material_suppliers.domain.RawMaterialSupplier;
import com.team1.team1project.raw_material_suppliers.repository.RawMaterialSupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RawMaterialSupplierServiceImpl implements RawMaterialSupplierService {

	@Autowired
	private RawMaterialSupplierRepository rawMaterialSupplierRepository;

	@Override
	public List<RawMaterialSupplier> getAllRawMaterialSuppliers() {
		return rawMaterialSupplierRepository.findAll();
	}

	@Override
	public Optional<RawMaterialSupplier> getRawMaterialSupplierById(int supplierId) {
		return rawMaterialSupplierRepository.findById(supplierId);
	}

	@Override
	public RawMaterialSupplier createRawMaterialSupplier(RawMaterialSupplier rawMaterialSupplier) {
		return rawMaterialSupplierRepository.save(rawMaterialSupplier);
	}

	@Override
	public RawMaterialSupplier updateRawMaterialSupplier(int supplierId, RawMaterialSupplier rawMaterialSupplier) {
		if (rawMaterialSupplierRepository.existsById(supplierId)) {
			rawMaterialSupplier.setSupplierId(supplierId);
			return rawMaterialSupplierRepository.save(rawMaterialSupplier);
		}
		return null;
	}

	@Override
	public boolean deleteRawMaterialSupplier(int supplierId) {
		if(rawMaterialSupplierRepository.existsById(supplierId)) {
			rawMaterialSupplierRepository.deleteById(supplierId);
		}

		return false;
	}

	@Override
	public Page<RawMaterialSupplier> getRawMaterialSupplierPage(Pageable pageable) {
		return rawMaterialSupplierRepository.findAll(pageable);
	}
}



