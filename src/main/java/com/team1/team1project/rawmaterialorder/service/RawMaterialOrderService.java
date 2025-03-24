package com.team1.team1project.rawmaterialorder.service;

import com.team1.team1project.rawmaterialorder.repository.RawMaterialOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RawMaterialOrderService {

    private final RawMaterialOrderRepository rawMaterialOrderRepository;
}

