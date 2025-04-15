package com.team1.team1project.controller;

import com.team1.team1project.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	
	@PostMapping("/csv/finishedProduct")
	public ResponseEntity<String> uploadFinishedProductCsv(@RequestParam("file") MultipartFile file) throws IOException {
		uploadService.processFinishedProductsCsv(file);
		String htmlResponse =
				"<html>" +
						"<head>" +
						"    <meta charset=\"UTF-8\">" +
						"    <script>" +
						"        alert('완제품 CSV 업로드 완료');" +
						"        window.location.href = '/finishedProduct/finishedProduct';" +
						"    </script>" +
						"</head>" +
						"<body></body>" +
						"</html>";


		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_HTML);

		return new ResponseEntity<>(htmlResponse, headers, HttpStatus.OK);
	}
}
