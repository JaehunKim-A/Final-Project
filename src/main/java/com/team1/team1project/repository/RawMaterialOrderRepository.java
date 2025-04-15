package com.team1.team1project.repository;

import com.team1.team1project.domain.RawMaterialOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawMaterialOrderRepository extends JpaRepository<RawMaterialOrder, Integer> {

}
