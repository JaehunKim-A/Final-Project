package com.team1.team1project.service;

import com.team1.team1project.dto.CustomerDTO;
import com.team1.team1project.service.customer.CustomerOrdersService;
import com.team1.team1project.service.customer.CustomerService;
import com.team1.team1project.dto.CustomerOrdersDTO;
import com.team1.team1project.dto.RawMaterialSupplierDTO;
import com.team1.team1project.service.rawMaterial.RawMaterialSupplierService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DownloadService {

	private final CustomerService customerService;
	private final RawMaterialSupplierService rawMaterialSupplierService;
	private final CustomerOrdersService customerOrdersService;

	public Workbook createExcelByType(String type) {
		switch (type) {
			case "customer":
				return createCustomerWorkbook();
			case "rawMaterialSupplier":
				return createSupplierWorkbook();
			case "customerOrders":
				return createOrdersWorkbook();
			default:
				throw new IllegalArgumentException("Unknown type: " + type);
		}
	}

	private Workbook createCustomerWorkbook() {
		List<CustomerDTO> customers = customerService.getAllCustomers();
		String[] headers = {"ID", "고객명", "전화번호", "주소", "등록일", "수정일"};

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Customers");
		createHeaderRow(sheet, headers);

		int rowNum = 1;
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		for (CustomerDTO c : customers) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(c.getCustomerId());
			row.createCell(1).setCellValue(c.getCustomerName());
			row.createCell(2).setCellValue(c.getContactInfo());
			row.createCell(3).setCellValue(c.getAddress());
			row.createCell(4).setCellValue(c.getRegDate().format(fmt));
			row.createCell(5).setCellValue(c.getModDate().format(fmt));
		}

		autoSizeColumns(sheet, headers.length);
		return workbook;
	}

	private Workbook createSupplierWorkbook() {
		List<RawMaterialSupplierDTO> suppliers = rawMaterialSupplierService.getAllRawMaterialSuppliers();
		String[] headers = {"ID", "공급처명", "연락처", "주소", "이메일", "전화번호", "등록일", "수정일"};

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Suppliers");
		createHeaderRow(sheet, headers);

		int rowNum = 1;
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		for (RawMaterialSupplierDTO s : suppliers) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(s.getSupplierId());
			row.createCell(1).setCellValue(s.getSupplierName());
			row.createCell(2).setCellValue(s.getContactInfo());
			row.createCell(3).setCellValue(s.getAddress());
			row.createCell(4).setCellValue(s.getEmail());
			row.createCell(5).setCellValue(s.getPhoneNumber());
			row.createCell(6).setCellValue(s.getRegDate().format(fmt));
			row.createCell(7).setCellValue(s.getModDate().format(fmt));
		}

		autoSizeColumns(sheet, headers.length);
		return workbook;
	}

	private Workbook createOrdersWorkbook() {
		List<CustomerOrdersDTO> orders = customerOrdersService.getAllCustomerOrders();
		String[] headers = {"주문코드", "고객코드", "주문일", "수량", "상태", "등록일", "수정일"};

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Orders");
		createHeaderRow(sheet, headers);

		int rowNum = 1;
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		for (CustomerOrdersDTO o : orders) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(o.getOrderId());
			row.createCell(1).setCellValue(o.getCustomerId());
			row.createCell(2).setCellValue(o.getOrderDate());
			if (o.getTotalAmount() != null) {
				row.createCell(3).setCellValue(o.getTotalAmount().doubleValue());
			} else {
				row.createCell(3).setCellValue("");
			}
			row.createCell(4).setCellValue(o.getStatus());
			row.createCell(5).setCellValue(o.getRegDate().format(fmt));
			row.createCell(6).setCellValue(o.getModDate().format(fmt));
		}

		autoSizeColumns(sheet, headers.length);
		return workbook;
	}

	public String createCsvByType(String type) {
		switch (type) {
			case "customer":
				return createCustomerCsv();
			case "rawMaterialSupplier":
				return createSupplierCsv();
			case "customerOrders":
				return createOrdersCsv();
			default:
				throw new IllegalArgumentException("Unknown type: " + type);
		}
	}

	private String createCustomerCsv() {
		List<CustomerDTO> customers = customerService.getAllCustomers();
		StringBuilder sb = new StringBuilder();
		sb.append("ID|고객명|전화번호|주소|등록일|수정일\n");
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		for (CustomerDTO c : customers) {
			sb.append(c.getCustomerId()).append("|")
					.append(c.getCustomerName()).append("|")
					.append(c.getContactInfo()).append("|")
					.append(c.getAddress()).append("|")
					.append(c.getRegDate().format(fmt)).append("|")
					.append(c.getModDate().format(fmt)).append("\n");
		}
		return sb.toString();
	}

	private String createSupplierCsv() {
		List<RawMaterialSupplierDTO> suppliers = rawMaterialSupplierService.getAllRawMaterialSuppliers();
		StringBuilder sb = new StringBuilder();
		sb.append("ID|공급처명|연락처|주소|등록일|수정일\n");
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		for (RawMaterialSupplierDTO s : suppliers) {
			sb.append(s.getSupplierId()).append("|")
					.append(s.getSupplierName()).append("|")
					.append(s.getContactInfo()).append("|")
					.append(s.getAddress()).append("|")
					.append(s.getEmail()).append("|")
					.append(s.getPhoneNumber()).append("|")
					.append(s.getRegDate().format(fmt)).append("|")
					.append(s.getModDate().format(fmt)).append("\n");
		}
		return sb.toString();
	}

	private String createOrdersCsv() {
		List<CustomerOrdersDTO> orders = customerOrdersService.getAllCustomerOrders();
		StringBuilder sb = new StringBuilder();
		sb.append("주문코드|고객코드|주문일|수량|상태|등록일|수정일\n");
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		for (CustomerOrdersDTO o : orders) {
			sb.append(o.getOrderId()).append("|")
					.append(o.getCustomerId()).append("|")
					.append(o.getOrderDate()).append("|")
					.append(o.getTotalAmount()).append("|")
					.append(o.getStatus()).append("|")
					.append(o.getRegDate().format(fmt)).append("|")
					.append(o.getModDate().format(fmt)).append("\n");
		}
		return sb.toString();
	}

	private void createHeaderRow(Sheet sheet, String[] headers) {
		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			headerRow.createCell(i).setCellValue(headers[i]);
		}
	}

	private void autoSizeColumns(Sheet sheet, int columnCount) {
		for (int i = 0; i < columnCount; i++) {
			sheet.autoSizeColumn(i);
		}
	}
}