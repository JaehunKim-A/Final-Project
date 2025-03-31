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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/download")
@RequiredArgsConstructor
public class DownloadController {

	private final DownloadService downloadService;

	@GetMapping("/excel/{type}")
	public void downloadExcel(@PathVariable String type, HttpServletResponse response) throws IOException {
		Workbook workbook = downloadService.createExcelByType(type);

		Map<String, String> fileNames = new HashMap<>();
		fileNames.put("customer", "고객_전체.xlsx");
		fileNames.put("rawMaterialSupplier", "공급자_전체.xlsx");

		String fileName = fileNames.getOrDefault(type, type + "_전체.xlsx");

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

		workbook.write(response.getOutputStream());
		workbook.close();
	}

	@GetMapping("/csv/{type}")
	public void downloadCsv(@PathVariable String type, HttpServletResponse response) throws IOException {
		String csvContent = downloadService.createCsvByType(type);

		Map<String, String> fileNames = new HashMap<>();
		fileNames.put("customer", "고객_전체.csv");
		fileNames.put("rawMaterialSupplier", "공급자_전체.csv");

		String fileName = fileNames.getOrDefault(type, type + "_전체.csv");

		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

		var out = response.getOutputStream();
		out.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}); // UTF-8 BOM
		out.write(csvContent.getBytes("UTF-8"));
		out.flush();
		out.close();
	}

	@GetMapping("/sample/customer")
	public void downloadCustomerSample(HttpServletResponse response) throws IOException {
		String sample = "customerName|contactInfo|address\n기존이름|변경연락처|변경주소\n";

		String fileName = "고객_업로드_샘플.csv";
		setCsvDownloadHeader(response, fileName);

		response.getWriter().write(sample);
		response.getWriter().flush();
	}

	@GetMapping("/sample/rawMaterialSupplier")
	public void downloadSupplierSample(HttpServletResponse response) throws IOException {
		String sample = "supplierName|contactInfo|address|email|phoneNumber\n기존이름|변경연락처|변경주소|변경이메일|변경전화번호\n";

		String fileName = "공급자_업로드_샘플.csv";
		setCsvDownloadHeader(response, fileName);

		response.getWriter().write(sample);
		response.getWriter().flush();
	}

	private void setCsvDownloadHeader(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
		String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8");

		// RFC 6266에 따라 filename과 filename*을 함께 제공
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''" + encodedFileName);
	}
}

