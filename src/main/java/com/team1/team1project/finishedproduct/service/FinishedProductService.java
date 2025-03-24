package com.team1.team1project.finishedproduct.service;

import com.team1.team1project.dto.FinishedProductsDTO;
import com.team1.team1project.finishedproduct.repository.FinishedProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface FinishedProductService {

    Long register(FinishedProductsDTO finishedProductsDTO);



}
