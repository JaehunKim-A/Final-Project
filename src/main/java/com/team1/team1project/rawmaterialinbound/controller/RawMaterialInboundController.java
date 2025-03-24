package com.team1.team1project.controller;

import com.team1.team1project.domain.RawMaterialInbound;
import com.team1.team1project.service.RawMaterialInboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/raw-material-inbounds")  // ✅ API 경로 설정
@RequiredArgsConstructor
public class RawMaterialInboundController {

    private final RawMaterialInboundService rawMaterialInboundService;

}
