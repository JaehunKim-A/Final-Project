package com.team1.team1project.controller;

import com.team1.team1project.domain.FinishedProductOutbound;
import com.team1.team1project.service.FinishedProductOutboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/finished-product-outbounds")  // ✅ API 기본 경로 설정
@RequiredArgsConstructor
public class FinishedProductOutboundController {

    private final FinishedProductOutboundService finishedProductOutboundService;

}
