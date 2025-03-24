package com.team1.team1project.raw_material_suppliers.repository;

import com.team1.team1project.raw_material_suppliers.domain.RawMaterialSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawMaterialSupplierRepository extends JpaRepository<RawMaterialSupplier, Integer> {
}
