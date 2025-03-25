package com.team1.team1project.service;

import com.team1.team1project.domain.RawMaterialSupplier;
import com.team1.team1project.raw_material_suppliers.service.RawMaterialSupplierService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RawMaterialSupplierServiceTests {

	@Autowired
	private RawMaterialSupplierService rawMaterialSupplierService;

	@Test
	public void testMofify() {
		RawMaterialSupplier rawMaterialSupplier = new RawMaterialSupplier(3, "ModifiedName", "MofifiedContact", "ModifiedAddress", "ModifiedEmail", "Modified123-456-7890");

		RawMaterialSupplier result = rawMaterialSupplierService.updateRawMaterialSupplier(3, rawMaterialSupplier);

	}
}
