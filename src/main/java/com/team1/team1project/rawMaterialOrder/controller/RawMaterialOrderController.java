package com.team1.team1project.rawMaterialOrder.controller;

import com.team1.team1project.rawMaterialOrder.service.RawMaterialOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/raw-material-orders")
@RequiredArgsConstructor
public class RawMaterialOrderController {

    private final RawMaterialOrderService rawMaterialOrderService;
}
