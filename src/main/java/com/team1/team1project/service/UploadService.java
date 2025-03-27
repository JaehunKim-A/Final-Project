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
				if (isFirst) { isFirst = false; continue; }

				String[] fields = line.split(",");
				String customerName = fields[0].trim();
				String contactInfo = fields[1].trim();
				String address = fields[2].trim();

				CustomerDTO existing = customerService.getCustomerByName(customerName);
				if (existing != null) {
					existing.setContactInfo(contactInfo);
					existing.setAddress(address);
					customerService.updateCustomer(existing.getCustomerId(), existing);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("CSV 처리 중 오류 발생", e);
		}
	}


	public void processSupplierCsv(MultipartFile file) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			boolean isFirst = true;
			while ((line = reader.readLine()) != null) {
				if (isFirst) { isFirst = false; continue; }

				String[] fields = line.split(",");

				if (fields.length < 5) {
					continue;
				}

				RawMaterialSupplierDTO dto = RawMaterialSupplierDTO.builder()
						.supplierName(fields[0].trim())
						.contactInfo(fields[1].trim())
						.address(fields[2].trim())
						.email(fields[3].trim())
						.phoneNumber(fields[4].trim())
						.build();

				rawMaterialSupplierService.saveOrUpdateByName(dto);
			}
		} catch (Exception e) {
			throw new RuntimeException("CSV 처리 중 오류 발생", e);
		}
	}

}