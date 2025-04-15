package com.team1.team1project.repository;

//import com.team1.team1project.codeManagement.repository.search.CodeManagementSearch;
import com.team1.team1project.domain.CodeManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeManagementRepository extends JpaRepository<CodeManagement, Long>/*, CodeManagementSearch */{
//    Page<CodeManagement> findByRegDateBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
//    Page<CodeManagement> findByRegDateAfter(LocalDateTime from, Pageable pageable);
//    Page<CodeManagement> findByRegDateBefore(LocalDateTime to, Pageable pageable);
}
