package com.team1.team1project.finishedProducts.service;

import com.team1.team1project.dto.FinishedProductsDTO;

public interface FinishedProductsService {
    // 등록 여러개 가능하도록
    Integer registers(FinishedProductsDTO finishedProductsDTO);
    // 1개 행 조회
    FinishedProductsDTO readOne(Integer productId);
    // 1개 행 수정
    void modifyOne(FinishedProductsDTO finishedProductsDTO);
    // 1개 행 삭제
    void removeOne(Integer productId);
}
