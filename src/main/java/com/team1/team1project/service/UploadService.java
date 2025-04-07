package com.team1.team1project.service;

import com.team1.team1project.customer.service.CustomerService;
import com.team1.team1project.customerOrders.service.CustomerOrdersService;
import com.team1.team1project.dto.CustomerDTO;
import com.team1.team1project.dto.CustomerOrdersDTO;
import com.team1.team1project.dto.RawMaterialSupplierDTO;
import com.team1.team1project.rawMaterialSuppliers.service.RawMaterialSupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class UploadService {

	private final CustomerService customerService;
	private final RawMaterialSupplierService rawMaterialSupplierService;
	private final CustomerOrdersService customerOrdersService;

	public void processCustomerCsv(MultipartFile file) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String header = reader.readLine();
			if (header == null) throw new RuntimeException("CSV 파일이 비어 있습니다.");
			int columnCount = header.split("\\|").length;

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty()) continue;
				String[] fields = line.split("\\|");

				if (fields.length != columnCount) {
					throw new RuntimeException("고객 CSV 형식 오류: [" + line + "] 필드 수가 예상과 다릅니다.");
				}

				if (columnCount == 4) {
					int customerId = Integer.parseInt(fields[0].trim());
					String name = fields[1].trim();
					String contact = fields[2].trim();
					String address = fields[3].trim();

					var existing = customerService.getCustomerById(customerId).orElse(null);
					if (existing != null) {
						existing.setCustomerName(name);
						existing.setContactInfo(contact);
						existing.setAddress(address);
						customerService.updateCustomer(customerId, existing);
					} else {
						CustomerDTO newCustomer = CustomerDTO.builder()
								.customerName(name)
								.contactInfo(contact)
								.address(address)
								.build();
						customerService.createCustomer(newCustomer);
					}
				} else if (columnCount == 3) {
					String name = fields[0].trim();
					String contact = fields[1].trim();
					String address = fields[2].trim();

					CustomerDTO newCustomer = CustomerDTO.builder()
							.customerName(name)
							.contactInfo(contact)
							.address(address)
							.build();
					customerService.createCustomer(newCustomer);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("고객 CSV 처리 중 오류 발생: " + e.getMessage(), e);
		}
	}

	public void processSupplierCsv(MultipartFile file) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String header = reader.readLine();
			if (header == null) throw new RuntimeException("CSV 파일이 비어 있습니다.");
			int columnCount = header.split("\\|").length;

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty()) continue;
				String[] fields = line.split("\\|");

				if (fields.length != columnCount) {
					throw new RuntimeException("공급사 CSV 형식 오류: [" + line + "] 필드 수가 예상과 다릅니다.");
				}

				if (columnCount == 6) {
					int supplierId = Integer.parseInt(fields[0].trim());
					String name = fields[1].trim();
					String contact = fields[2].trim();
					String address = fields[3].trim();
					String email = fields[4].trim();
					String phone = fields[5].trim();

					var existing = rawMaterialSupplierService.getRawMaterialSupplierById(supplierId).orElse(null);
					if (existing != null) {
						existing.setSupplierName(name);
						existing.setContactInfo(contact);
						existing.setAddress(address);
						existing.setEmail(email);
						existing.setPhoneNumber(phone);
						rawMaterialSupplierService.updateRawMaterialSupplier(supplierId, existing);
					} else {
						RawMaterialSupplierDTO newSupplier = RawMaterialSupplierDTO.builder()
								.supplierName(name)
								.contactInfo(contact)
								.address(address)
								.email(email)
								.phoneNumber(phone)
								.build();
						rawMaterialSupplierService.createRawMaterialSupplier(newSupplier);
					}
				} else if (columnCount == 5) {
					String name = fields[0].trim();
					String contact = fields[1].trim();
					String address = fields[2].trim();
					String email = fields[3].trim();
					String phone = fields[4].trim();

					RawMaterialSupplierDTO newSupplier = RawMaterialSupplierDTO.builder()
							.supplierName(name)
							.contactInfo(contact)
							.address(address)
							.email(email)
							.phoneNumber(phone)
							.build();
					rawMaterialSupplierService.createRawMaterialSupplier(newSupplier);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("공급사 CSV 처리 중 오류 발생: " + e.getMessage(), e);
		}
	}

	public void processOrdersCsv(MultipartFile file) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String header = reader.readLine();
			if (header == null) throw new RuntimeException("CSV 파일이 비어 있습니다.");
			int columnCount = header.split("\\|").length;

			String line;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty()) continue;
				String[] fields = line.split("\\|");

				if (fields.length != columnCount) {
					throw new RuntimeException("주문 CSV 형식 오류: [" + line + "] 필드 수가 예상과 다릅니다.");
				}

				if (columnCount == 5) {
					int orderId = Integer.parseInt(fields[0].trim());
					int customerId = Integer.parseInt(fields[1].trim());

					if (!customerService.getCustomerById(customerId).isPresent()) {
						throw new RuntimeException("주문 CSV 오류: customerId [" + customerId + "] 는 존재하지 않는 고객입니다.");
					}

					LocalDateTime orderDate = LocalDateTime.parse(fields[2].trim(), formatter);
					BigDecimal totalAmount = new BigDecimal(fields[3].trim());
					String status = fields[4].trim();

					var existing = customerOrdersService.getCustomerOrderById(orderId).orElse(null);
					if (existing != null) {
						existing.setCustomerId(customerId);
						existing.setOrderDate(orderDate);
						existing.setTotalAmount(totalAmount);
						existing.setStatus(status);
						customerOrdersService.updateCustomerOrder(orderId, existing);
					} else {
						CustomerOrdersDTO newOrder = CustomerOrdersDTO.builder()
								.customerId(customerId)
								.orderDate(orderDate)
								.totalAmount(totalAmount)
								.status(status)
								.build();
						customerOrdersService.createCustomerOrder(newOrder);
					}
				} else if (columnCount == 4) {
					int customerId = Integer.parseInt(fields[0].trim());
					LocalDateTime orderDate = LocalDateTime.parse(fields[1].trim(), formatter);
					BigDecimal totalAmount = new BigDecimal(fields[2].trim());
					String status = fields[3].trim();

					CustomerOrdersDTO newOrder = CustomerOrdersDTO.builder()
							.customerId(customerId)
							.orderDate(orderDate)
							.totalAmount(totalAmount)
							.status(status)
							.build();
					customerOrdersService.createCustomerOrder(newOrder);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("주문 CSV 처리 중 오류 발생: " + e.getMessage(), e);
		}
	}
}
