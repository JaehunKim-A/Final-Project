package com.team1.team1project.service.finishedProduct;

import com.team1.team1project.domain.FinishedProducts;
import com.team1.team1project.dto.FinishedProductsDTO;

import java.util.List;

public interface FinishedProductsService {
    // 등록 여러개 가능하도록
    Long registers(FinishedProductsDTO finishedProductsDTO);
    // 1개 행 조회
    FinishedProductsDTO readOne(Long productId);
    // 1개 행 수정
    void modifyOne(FinishedProductsDTO finishedProductsDTO);
    // 1개 행 삭제
    void removeOne(Long productId);

    List<FinishedProducts> getAllProducts();

}
