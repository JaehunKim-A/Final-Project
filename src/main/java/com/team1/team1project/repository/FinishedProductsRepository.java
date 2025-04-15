package com.team1.team1project.repository;

import com.team1.team1project.domain.FinishedProducts;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinishedProductsRepository extends JpaRepository<FinishedProducts, Long> {

    Optional<FinishedProducts> findByProductCode(String productCode);
}
