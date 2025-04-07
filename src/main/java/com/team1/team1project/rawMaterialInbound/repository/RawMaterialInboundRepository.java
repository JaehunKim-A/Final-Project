package com.team1.team1project.rawMaterialInbound.repository;

import com.team1.team1project.domain.RawMaterialInbound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawMaterialInboundRepository extends JpaRepository<RawMaterialInbound, Long> {
}
