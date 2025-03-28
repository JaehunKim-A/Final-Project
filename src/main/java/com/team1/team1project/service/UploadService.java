package com.team1.team1project.service;

import com.team1.team1project.customer.service.CustomerService;
import com.team1.team1project.dto.CustomerDTO;
import com.team1.team1project.dto.RawMaterialSupplierDTO;
import com.team1.team1project.rawMaterialSuppliers.service.RawMaterialSupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class UploadService {

	private final CustomerService customerService;
	private final RawMaterialSupplierService rawMaterialSupplierService;

	public void processCustomerCsv(MultipartFile file) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			boolean isFirst = true;

			while ((line = reader.readLine()) != null) {
				if (isFirst) {
					isFirst = false;
					continue;
				}

				if (line.trim().isEmpty()) continue;

				String[] fields = line.split("\\|");
				if (fields.length < 3) {
					System.err.println("잘못된 고객 CSV 라인 (필드 부족): " + line);
					continue;
				}

				String customerName = fields[0].trim();
				String contactInfo = fields[1].trim();
				String address = fields[2].trim();

				CustomerDTO existing = customerService.getCustomerByName(customerName);
				if (existing != null) {
					existing.setContactInfo(contactInfo);
					existing.setAddress(address);
					customerService.updateCustomer(existing.getCustomerId(), existing);
				} else {
					System.out.println("등록되지 않은 고객 이름: " + customerName);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("고객 CSV 처리 중 오류 발생", e);
		}
	}

	public void processSupplierCsv(MultipartFile file) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			boolean isFirst = true;

			while ((line = reader.readLine()) != null) {
				if (isFirst) {
					isFirst = false;
					continue;
				}

				if (line.trim().isEmpty()) continue;

				String[] fields = line.split("\\|");
				if (fields.length < 5) {
					System.err.println("잘못된 공급사 CSV 라인 (필드 부족): " + line);
					continue;
				}

				String supplierName = fields[0].trim();
				String contactInfo = fields[1].trim();
				String address = fields[2].trim();
				String email = fields[3].trim();
				String phoneNumber = fields[4].trim();

				RawMaterialSupplierDTO existing = rawMaterialSupplierService.getRawMaterialSupplierByName(supplierName);
				if (existing != null) {
					existing.setContactInfo(contactInfo);
					existing.setAddress(address);
					existing.setEmail(email);
					existing.setPhoneNumber(phoneNumber);
					rawMaterialSupplierService.updateRawMaterialSupplier(existing.getSupplierId(), existing);
				} else {
					System.out.println("등록되지 않은 공급사 이름: " + supplierName);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("공급사 CSV 처리 중 오류 발생", e);
		}
	}
}
