package com.team1.team1project.repository;

import com.team1.team1project.domain.FinishedProductOutbound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FinishedProductOutboundRepository extends JpaRepository<FinishedProductOutbound, Long> {

    /**
     * ✅ 검색 + 페이징 처리 쿼리
     * - keyword가 없으면 전체 조회
     */
    @Query("SELECT f FROM FinishedProductOutbound f " +
            "WHERE (:keyword IS NULL OR :keyword = '' " +
            "OR LOWER(f.status) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(f.outboundCode) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<FinishedProductOutbound> searchWithPaging(String keyword, Pageable pageable);

}
