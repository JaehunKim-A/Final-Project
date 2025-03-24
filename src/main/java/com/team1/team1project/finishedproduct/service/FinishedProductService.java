package com.team1.team1project.finishedproduct.service;

import com.team1.team1project.finishedproduct.repository.FinishedProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinishedProductService {

    private final FinishedProductRepository finishedProductRepository;

}
