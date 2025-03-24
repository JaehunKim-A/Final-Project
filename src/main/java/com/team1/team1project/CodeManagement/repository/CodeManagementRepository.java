package com.team1.team1project.CodeManagement.repository;

import com.team1.team1project.domain.CodeManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeManagementRepository extends JpaRepository<CodeManagement, Integer> {

}
