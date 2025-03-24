package com.team1.team1project.finishedproduct.controller;

import com.team1.team1project.finishedproduct.service.FinishedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/finished-products")
@RequiredArgsConstructor
public class FinishedProductController {

    private final FinishedProductService finishedProductService;

}
