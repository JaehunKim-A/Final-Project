package com.team1.team1project.finishedproduct.repository;

import com.team1.team1project.domain.FinishedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinishedProductRepository extends JpaRepository<FinishedProduct, Integer> {
    // 추가적인 쿼리 메소드가 필요하다면 여기에 작성
}
