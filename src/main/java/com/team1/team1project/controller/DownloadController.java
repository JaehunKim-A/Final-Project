package com.team1.team1project.controller;

import com.team1.team1project.service.DownloadService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/download")
@RequiredArgsConstructor
public class DownloadController {

	private final DownloadService downloadService;

	@GetMapping("/excel/{type}")
	public void downloadExcel(@PathVariable String type, HttpServletResponse response) throws IOException {
		Workbook workbook = downloadService.createExcelByType(type);

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=" + type + ".xlsx");

		workbook.write(response.getOutputStream());
		workbook.close();
	}

	@GetMapping("/csv/{type}")
	public void downloadCsv(@PathVariable String type, HttpServletResponse response) throws IOException {
		String csvContent = downloadService.createCsvByType(type);

		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + type + ".csv");

		var out = response.getOutputStream();
		out.write(new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF}); // BOM
		out.write(csvContent.getBytes("UTF-8")); // 문자열 → 바이트
		out.flush();
		out.close();
	}

	@GetMapping("/sample/customer")
	public void downloadCustomerSample(HttpServletResponse response) throws IOException {
		String sample = "customerName|contactInfo|address\n기존이름|변경연락처|변경주소\n";

		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=customer_sample.csv");

		response.getWriter().write(sample);
		response.getWriter().flush();
	}

	@GetMapping("/sample/rawMaterialSupplier")
	public void downloadSupplierSample(HttpServletResponse response) throws IOException {
		String sample = "supplierName|contactInfo|address|email|phoneNumber\n기존이름|변경연락처|변경주소|변경이메일|변경전화번호\n";

		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=supplier_sample.csv");

		response.getWriter().write(sample);
		response.getWriter().flush();
	}
}
