package com.team1.team1project.repository;

import com.team1.team1project.domain.RawMaterialInbound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawMaterialInboundRepository extends JpaRepository<RawMaterialInbound, Integer> {
    // 기본적인 CRUD 메서드 제공
    // 추가적인 쿼리 메서드가 필요하면 여기 추가 가능
}
