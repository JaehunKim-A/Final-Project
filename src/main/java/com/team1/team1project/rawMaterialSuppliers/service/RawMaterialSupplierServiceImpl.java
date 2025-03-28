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
		Optional<RawMaterialSupplier> optional = rawMaterialSupplierRepository.findById(supplierId);

		if (optional.isPresent()) {
			RawMaterialSupplier existing = optional.get();

			existing.setSupplierName(rawMaterialSupplierDTO.getSupplierName());
			existing.setContactInfo(rawMaterialSupplierDTO.getContactInfo());
			existing.setAddress(rawMaterialSupplierDTO.getAddress());
			existing.setEmail(rawMaterialSupplierDTO.getEmail());
			existing.setPhoneNumber(rawMaterialSupplierDTO.getPhoneNumber());
			existing.setModDate(LocalDateTime.now()); // 수정일 갱신

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
	public void saveOrUpdate(RawMaterialSupplierDTO rawMaterialSupplierDTO) {
		Optional<RawMaterialSupplierDTO> existing = getRawMaterialSupplierById(rawMaterialSupplierDTO.getSupplierId());
		if(existing.isPresent()) {
			updateRawMaterialSupplier(rawMaterialSupplierDTO.getSupplierId(), rawMaterialSupplierDTO);
		} else {
			createRawMaterialSupplier(rawMaterialSupplierDTO);
		}
	}

	@Override
	public RawMaterialSupplierDTO getRawMaterialSupplierByName(String supplierName) {
		RawMaterialSupplier rawMaterialSupplier = rawMaterialSupplierRepository.findBySupplierName(supplierName);
		return modelMapper.map(rawMaterialSupplier, RawMaterialSupplierDTO.class);
	}

	@Override
	public void updateRawMaterialSupplier(Integer supplierId, RawMaterialSupplierDTO rawMaterialSupplierDTO) {
		RawMaterialSupplier rawMaterialSupplier = rawMaterialSupplierRepository.findById(supplierId)
				.orElseThrow(() -> new IllegalArgumentException("공급자 없음"));
		rawMaterialSupplier.setContactInfo(rawMaterialSupplierDTO.getContactInfo());
		rawMaterialSupplier.setAddress(rawMaterialSupplierDTO.getAddress());
		rawMaterialSupplier.setEmail(rawMaterialSupplierDTO.getEmail());
		rawMaterialSupplier.setPhoneNumber(rawMaterialSupplier.getPhoneNumber());
		rawMaterialSupplierRepository.save(rawMaterialSupplier);
	}
}
