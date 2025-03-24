package com.team1.team1project.repository;

import com.team1.team1project.domain.FinishedProductOutbound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinishedProductOutboundRepository extends JpaRepository<FinishedProductOutbound, Integer> {

}
