package com.team1.team1project.rawMaterialSuppliers.repository;

import com.team1.team1project.domain.RawMaterialSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawMaterialSupplierRepository extends JpaRepository<RawMaterialSupplier, Integer> {

}
