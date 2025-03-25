package com.team1.team1project.rawMaterialOrder.service;

import com.team1.team1project.rawMaterialOrder.repository.RawMaterialOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RawMaterialOrderService {

    private final RawMaterialOrderRepository rawMaterialOrderRepository;
}

