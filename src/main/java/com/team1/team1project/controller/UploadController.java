package com.team1.team1project.controller;

import com.team1.team1project.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/csv/employee")
    public ResponseEntity<String> uploadEmployeeCsv(@RequestParam("file") MultipartFile file) throws IOException {
        uploadService.processEmployeeCsv(file);
        return ResponseEntity.ok("사원 CSV 업로드 완료");
    }


}