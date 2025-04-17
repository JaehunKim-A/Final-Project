package com.team1.team1project.repository;

import com.team1.team1project.domain.RawMaterialInbound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RawMaterialInboundRepository extends JpaRepository<RawMaterialInbound, Long> {

    /**
     * ✅ 검색 + 페이징 처리 쿼리
     * - keyword가 없으면 전체 조회
     * - keyword는 productName, lotNumber, status 등에 적용 가능
     */
    @Query("SELECT f FROM RawMaterialInbound f " +
            "WHERE (:keyword IS NULL OR :keyword = '' " +
            "OR LOWER(f.status) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(f.inboundCode) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<RawMaterialInbound> searchWithPaging(String keyword, Pageable pageable);

}
