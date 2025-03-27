package com.team1.team1project.rawMaterialSuppliers.repository;

import com.team1.team1project.domain.RawMaterialSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RawMaterialSupplierRepository extends JpaRepository<RawMaterialSupplier, Integer> {
	Optional<RawMaterialSupplier> findBySupplierName(String supplierName);

}
