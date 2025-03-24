package com.team1.team1project.finishedProducts.repository;

import com.team1.team1project.domain.FinishedProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinishedProductsRepository extends JpaRepository<FinishedProducts, Integer> {

    // 위에서 부터 product_id 찾기

}
