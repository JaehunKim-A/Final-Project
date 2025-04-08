package com.team1.team1project.repository;

import com.team1.team1project.domain.FinishedProductOutbound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinishedProductOutboundRepository extends JpaRepository<FinishedProductOutbound, Long> {
    List<FinishedProductOutbound> findAll();
}
