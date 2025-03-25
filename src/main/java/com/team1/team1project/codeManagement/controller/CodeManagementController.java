package com.team1.team1project.codeManagement.controller;

import com.team1.team1project.codeManagement.service.CodeManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/code-managements")
@RequiredArgsConstructor
public class CodeManagementController {

    private final CodeManagementService codeManagementService;

    // 코드 목록 가져오기
}
