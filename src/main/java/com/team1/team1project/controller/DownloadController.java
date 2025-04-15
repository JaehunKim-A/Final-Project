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
		fileNames.put("customerOrders", "주문_전체.xlsx");

		fileNames.put("finishedProduct", "제품_전체.xlsx");
		fileNames.put("rawMaterial", "원자재_전체.xlsx");
		fileNames.put("codeManagement", "코드_전체.xlsx");

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
		fileNames.put("customerOrders", "주문_전체.csv");

		fileNames.put("finishedProduct", "제품_전체.xlsx");
		fileNames.put("rawMaterial", "원자재_전체.xlsx");
		fileNames.put("codeManagement", "코드_전체.xlsx");

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
		StringBuilder sample = new StringBuilder();

		sample.append("1. 신규등록(업로드시 이 행은 삭제)\n");
		sample.append("customerName|contactInfo|address\n");
		sample.append("신규이름|신규연락처|신규주소\n\n");

		sample.append("2. 수정(업로드시 이 행은 삭제)\n");
		sample.append("customerId|customerName|contactInfo|address\n");
		sample.append("기존코드|변경이름|변경연락처|변경주소\n");

		String fileName = "고객_업로드_샘플.csv";
		setCsvDownloadHeader(response, fileName);

		response.getWriter().write(sample.toString());
		response.getWriter().flush();
	}

	@GetMapping("/sample/rawMaterialSupplier")
	public void downloadSupplierSample(HttpServletResponse response) throws IOException {
		StringBuilder sample = new StringBuilder();

		sample.append("1. 신규등록(업로드시 이 행은 삭제)\n");
		sample.append("supplierName|contactInfo|address|email|phoneNumber\n");
		sample.append("신규이름|신규연락처|주소|변경이메일|변경전화번호\n\n");

		sample.append("2. 수정(업로드시 이 행은 삭제)\n");
		sample.append("supplierId|supplierName|contactInfo|address|email|phoneNumber\n");
		sample.append("기존공급자코드|변경이름|변경연락처|변경주소|변경이메일|변경전화번호\n");

		String fileName = "공급자_업로드_샘플.csv";
		setCsvDownloadHeader(response, fileName);

		response.getWriter().write(sample.toString());
		response.getWriter().flush();
	}

	@GetMapping("/sample/customerOrders")
	public void downloadOrdersSample(HttpServletResponse response) throws IOException {
		StringBuilder sample = new StringBuilder();

		sample.append("1. 신규등록(업로드시 이 행은 삭제)\n");
		sample.append("customerId|orderDate|totalAmount|status\n");
		sample.append("신규고객코드|주문일|수량|상태\n\n");

		sample.append("2. 수정(업로드시 이 행은 삭제)\n");
		sample.append("orderId|customerId|orderDate|totalAmount|status\n");
		sample.append("기존주문코드|변경고객코드|주문일|수량|상태\n");

		String fileName = "주문_업로드_샘플.csv";
		setCsvDownloadHeader(response, fileName);

		response.getWriter().write(sample.toString());
		response.getWriter().flush();
	}

	private void setCsvDownloadHeader(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
		String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8");

		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + encodedFileName + "\"; filename*=UTF-8''" + encodedFileName);
	}

	@GetMapping("/sample/codeManagement")
	public void downloadCodeManagementSample(HttpServletResponse response) throws IOException {
		StringBuilder sample = new StringBuilder();

		sample.append("1. 신규등록(업로드시 이 행은 삭제)\n");
		sample.append("codeValue|codeName|type|category|description\n");
		sample.append("코드|이름|타입|카테고리|설명\n\n");

		sample.append("2. 수정(업로드시 이 행은 삭제)\n");
		sample.append("codeValue|codeName|type|category|description\n");
		sample.append("변경코드|변경이름|변경타입|변경카테고리|변경설명\n");

		String fileName = "코드_업로드_샘플.csv";
		setCsvDownloadHeader(response, fileName);

		response.getWriter().write(sample.toString());
		response.getWriter().flush();
	}

	@GetMapping("/sample/rawMaterial")
	public void downloadRawMaterialSample(HttpServletResponse response) throws IOException {
		StringBuilder sample = new StringBuilder();

		sample.append("1. 신규등록(업로드시 이 행은 삭제)\n");
		sample.append("materialCode|materialName|category|unit|description\n");
		sample.append("신규코드|신규이름|카테고리|포장유닛|설명\n\n");

		sample.append("2. 수정(업로드시 이 행은 삭제)\n");
		sample.append("materialCode|materialName|category|unit|description\n");
		sample.append("기존원자재코드|변경이름|변경카테고리|변경유닛|변경설명\n");

		String fileName = "원자재_업로드_샘플.csv";
		setCsvDownloadHeader(response, fileName);

		response.getWriter().write(sample.toString());
		response.getWriter().flush();
	}

	@GetMapping("/sample/finishedProduct")
	public void downloadProductSample(HttpServletResponse response) throws IOException {
		StringBuilder sample = new StringBuilder();

		sample.append("1. 신규등록(업로드시 이 행은 삭제)\n");
		sample.append("productCode|productName|category|unit|status|description\n");
		sample.append("신규제품코드|이름|카테고리|유닛|상태|설명\n\n");

		sample.append("2. 수정(업로드시 이 행은 삭제)\n");
		sample.append("productCode|productName|category|unit|status|description\n");
		sample.append("기존제품코드|변경제품이름|카테고리|유닛|상태|설명\n");

		String fileName = "제품_업로드_샘플.csv";
		setCsvDownloadHeader(response, fileName);

		response.getWriter().write(sample.toString());
		response.getWriter().flush();
	}
}

