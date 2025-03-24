package com.team1.team1project.finishedproducts.repository;

import com.team1.team1project.domain.FinishedProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinishedProductsRepository extends JpaRepository<FinishedProducts, Integer> {

    // 위에서 부터 product_id 찾기

}
