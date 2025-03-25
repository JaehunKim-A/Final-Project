package com.team1.team1project.finishedProductInbound.repository;

import com.team1.team1project.domain.FinishedProductInbound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinishedProductInboundRepository extends JpaRepository<FinishedProductInbound, Integer> {
    // 추가적인 쿼리 메소드가 필요하다면 여기에 작성
}
