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

	@PostMapping("/csv/customer")
	public ResponseEntity<String> uploadCustomerCsv(@RequestParam("file") MultipartFile file) throws IOException {
		uploadService.processCustomerCsv(file);
		return ResponseEntity.ok("고객 CSV 업로드 완료");
	}

	@PostMapping("/csv/rawMaterialSupplier")
	public ResponseEntity<String> uploadSupplierCsv(@RequestParam("file") MultipartFile file) throws IOException {
		uploadService.processSupplierCsv(file);
		return ResponseEntity.ok("공급처 CSV 업로드 완료");
	}
	@PostMapping("/csv/customerOrders")
	public ResponseEntity<String> uploadOrdersCsv(@RequestParam("file") MultipartFile file) throws IOException {
		uploadService.processOrdersCsv(file);
		return ResponseEntity.ok("주문 CSV 업로드 완료");
	}
}
