package com.team1.team1project.repository;

import com.team1.team1project.domain.FinishedProductInbound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinishedProductInboundRepository extends JpaRepository<FinishedProductInbound, Long> {
        List<FinishedProductInbound> findAll();
}
