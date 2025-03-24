package com.team1.team1project.finishedproducts.controller;

import com.team1.team1project.finishedproducts.service.FinishedProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finished-products")
@RequiredArgsConstructor
public class FinishedProductsController {

    private final FinishedProductsService finishedProductService;


}
