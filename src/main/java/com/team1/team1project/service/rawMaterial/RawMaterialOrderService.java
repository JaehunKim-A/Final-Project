package com.team1.team1project.service.rawMaterial;

import com.team1.team1project.repository.RawMaterialOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RawMaterialOrderService {

    private final RawMaterialOrderRepository rawMaterialOrderRepository;
}

