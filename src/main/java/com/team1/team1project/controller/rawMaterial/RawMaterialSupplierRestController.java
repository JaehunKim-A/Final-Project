package com.team1.team1project.controller.rawMaterial;

import com.team1.team1project.dto.RawMaterialSupplierDTO;
import com.team1.team1project.service.rawMaterial.RawMaterialSupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/rawMaterialSupplier")
public class RawMaterialSupplierRestController {

	private final RawMaterialSupplierService rawMaterialSupplierService;

	@GetMapping("/all")
	public List<RawMaterialSupplierDTO> getAllRawMaterialSuppliers() {
		return rawMaterialSupplierService.getAllRawMaterialSuppliers();
	}

	@PostMapping
	public ResponseEntity<Void> registerSupplier(@RequestBody RawMaterialSupplierDTO dto) {
		rawMaterialSupplierService.createRawMaterialSupplier(dto);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateSupplier(@PathVariable int id, @RequestBody RawMaterialSupplierDTO dto) {
		rawMaterialSupplierService.updateRawMaterialSupplier(id, dto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSupplier(@PathVariable int id) {
		rawMaterialSupplierService.deleteRawMaterialSupplier(id);
		return ResponseEntity.ok().build();
	}
}
