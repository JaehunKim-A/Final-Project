package com.team1.team1project.repository;

import com.team1.team1project.domain.RawMaterialOutbound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RawMaterialOutboundRepository extends JpaRepository<RawMaterialOutbound, Long> {

    /**
     * ✅ 검색 + 페이징 처리 쿼리
     * - keyword가 없으면 전체 조회
     */
    @Query("SELECT r FROM RawMaterialOutbound r " +
            "WHERE (:keyword IS NULL OR :keyword = '' " +
            "OR LOWER(r.status) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(r.outboundCode) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<RawMaterialOutbound> searchWithPaging(String keyword, Pageable pageable);

}
