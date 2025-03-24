package com.team1.team1project.CodeManagement.service;

import com.team1.team1project.CodeManagement.repository.CodeManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeManagementService {

    private final CodeManagementRepository codeManagementRepository;

    // 비즈니스 로직 추가
}