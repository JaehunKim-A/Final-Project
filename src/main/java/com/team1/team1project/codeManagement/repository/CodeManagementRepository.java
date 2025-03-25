package com.team1.team1project.codeManagement.repository;

import com.team1.team1project.domain.CodeManagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CodeManagementRepository extends JpaRepository<CodeManagement, Long> {
    Page<CodeManagement> findByRegDateBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
    Page<CodeManagement> findByRegDateAfter(LocalDateTime from, Pageable pageable);
    Page<CodeManagement> findByRegDateBefore(LocalDateTime to, Pageable pageable);
}
