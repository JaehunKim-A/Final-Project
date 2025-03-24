package com.team1.team1project.finishedproductinbound.controller;

import com.team1.team1project.domain.FinishedProductInbound;
import com.team1.team1project.finishedproductinbound.service.FinishedProductInboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/finished-product-inbounds")
@RequiredArgsConstructor
public class FinishedProductInboundController {

    private final FinishedProductInboundService finishedProductInboundService;

}
