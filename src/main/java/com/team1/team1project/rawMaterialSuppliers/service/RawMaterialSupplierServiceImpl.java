package com.team1.team1project.rawMaterialSuppliers.service;

import com.team1.team1project.domain.RawMaterialSupplier;
import com.team1.team1project.dto.RawMaterialSupplierDTO;
import com.team1.team1project.rawMaterialSuppliers.repository.RawMaterialSupplierRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		Optional<RawMaterialSupplier> optional = rawMaterialSupplierRepository.findById(supplierId);

		if (optional.isPresent()) {
			RawMaterialSupplier existing = optional.get();

			existing.setSupplierName(rawMaterialSupplierDTO.getSupplierName());
			existing.setContactInfo(rawMaterialSupplierDTO.getContactInfo());
			existing.setAddress(rawMaterialSupplierDTO.getAddress());
			existing.setEmail(rawMaterialSupplierDTO.getEmail());
			existing.setPhoneNumber(rawMaterialSupplierDTO.getPhoneNumber());

			RawMaterialSupplier updated = rawMaterialSupplierRepository.save(existing);
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

	@Override
	public Optional<RawMaterialSupplierDTO> getRawMaterialSupplierByName(String supplierName) {
		return rawMaterialSupplierRepository.findBySupplierName(supplierName)
				.map(supplier -> modelMapper.map(supplier, RawMaterialSupplierDTO.class));
	}

	@Transactional
	@Override
	public void saveOrUpdateByName(RawMaterialSupplierDTO dto) {
		Optional<RawMaterialSupplierDTO> existing = getRawMaterialSupplierByName(dto.getSupplierName());

		if (existing.isPresent()) {
			updateRawMaterialSupplier(existing.get().getSupplierId(), dto);
		} else {
			createRawMaterialSupplier(dto);
		}
	}
}
