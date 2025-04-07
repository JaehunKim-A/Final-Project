package com.team1.team1project.productProcessManagement.repository;

import com.team1.team1project.domain.MachineHistory;
import com.team1.team1project.productProcessManagement.repository.search.MachineHistorySearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MachineHistoryRepository extends JpaRepository<MachineHistory, Integer>, MachineHistorySearch {
}
