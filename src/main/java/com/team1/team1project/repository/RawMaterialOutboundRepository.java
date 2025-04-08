package com.team1.team1project.repository;

import com.team1.team1project.domain.RawMaterialOutbound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawMaterialOutboundRepository extends JpaRepository<RawMaterialOutbound, Long> {
    // 기본 CRUD 기능은 JpaRepository에서 모두 제공됩니다.
}
