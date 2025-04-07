package com.team1.team1project.rawMaterialSuppliers.service;

import com.team1.team1project.domain.RawMaterialSupplier;
import com.team1.team1project.dto.RawMaterialSupplierDTO;
import com.team1.team1project.rawMaterialSuppliers.repository.RawMaterialSupplierRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RawMaterialSupplierServiceImpl implements RawMaterialSupplierService {

	private final RawMaterialSupplierRepository rawMaterialSupplierRepository;
	private final ModelMapper modelMapper;

	@Override
	public List<RawMaterialSupplierDTO> getAllRawMaterialSuppliers() {
		return rawMaterialSupplierRepository.findAll().stream()
				.map(supplier -> modelMapper.map(supplier, RawMaterialSupplierDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<RawMaterialSupplierDTO> getRawMaterialSupplierById(int supplierId) {
		return rawMaterialSupplierRepository.findById(supplierId)
				.map(supplier -> modelMapper.map(supplier, RawMaterialSupplierDTO.class));
	}

	@Override
	public RawMaterialSupplierDTO createRawMaterialSupplier(RawMaterialSupplierDTO rawMaterialSupplierDTO) {
		RawMaterialSupplier supplier = modelMapper.map(rawMaterialSupplierDTO, RawMaterialSupplier.class);
		RawMaterialSupplier saved = rawMaterialSupplierRepository.save(supplier);
		return modelMapper.map(saved, RawMaterialSupplierDTO.class);
	}

	@Override
	public RawMaterialSupplierDTO updateRawMaterialSupplier(int supplierId, RawMaterialSupplierDTO rawMaterialSupplierDTO) {
		if (rawMaterialSupplierRepository.existsById(supplierId)) {
			RawMaterialSupplier existing = rawMaterialSupplierRepository.findById(supplierId).get();

			RawMaterialSupplier supplier = modelMapper.map(rawMaterialSupplierDTO, RawMaterialSupplier.class);
			supplier.setSupplierId(supplierId);
			supplier.setRegDate(existing.getRegDate());
			supplier.setModDate(LocalDateTime.now());

			RawMaterialSupplier updated = rawMaterialSupplierRepository.save(supplier);
			return modelMapper.map(updated, RawMaterialSupplierDTO.class);
		}
		return null;
	}

	@Override
	public boolean deleteRawMaterialSupplier(int supplierId) {
		if (rawMaterialSupplierRepository.existsById(supplierId)) {
			rawMaterialSupplierRepository.deleteById(supplierId);
			return true;
		}
		return false;
	}

}
