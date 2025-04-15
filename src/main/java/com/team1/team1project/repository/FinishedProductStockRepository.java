package com.team1.team1project.repository;

import com.team1.team1project.domain.FinishedProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinishedProductStockRepository extends JpaRepository<FinishedProductStock, Long> {

}
