package com.team1.team1project.raw_material_suppliers.controller;

import com.team1.team1project.domain.RawMaterialSupplier;
import com.team1.team1project.raw_material_suppliers.service.RawMaterialSupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class RawMaterialSupplierController {

	@Autowired
	private final RawMaterialSupplierService rawMaterialSupplierService;

	// 고객 목록을 보여주는 메서드
	@GetMapping("/table/raw_material_supplier")
	public String showRawMaterialSupplierList(Model model) {
		List<RawMaterialSupplier> rawMaterialSuppliers = rawMaterialSupplierService.getAllRawMaterialSuppliers();
		// 컬럼 이름 리스트
		List<String> columnNames = List.of(
				"supplierId", "supplierName", "contactInfo", "address", "email", "phone_number", "reg_date", "mod_date"
		);

		model.addAttribute("rawMaterialSuppliers", rawMaterialSuppliers);
		model.addAttribute("columns", columnNames);

		return "raw_material_supplier/table";
	}



	// 고객 등록 폼을 표시하는 메서드
	@GetMapping("/table/raw_material_supplier/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("raw_material_supplier", new RawMaterialSupplier()); // 새 고객 객체를 모델에 추가
		return "raw_material_supplier/register"; // 고객 등록 페이지로 이동
	}

	// 고객 등록 처리 메서드
	@PostMapping("/table/raw_material_supplier/register")
	public String registerRaw_material_supplier(@ModelAttribute RawMaterialSupplier rawMaterialSupplier) {
		rawMaterialSupplierService.createRawMaterialSupplier(rawMaterialSupplier); // 고객 등록
		return "redirect:/table/raw_material_supplier"; // 고객 목록 페이지로 리다이렉트
	}

	// 고객 수정 폼을 표시하는 메서드
	@GetMapping("/table/raw_material_supplier/edit/{supplierId}")
	public String showEditForm(@PathVariable("supplierId") int supplierId, Model model) {
		RawMaterialSupplier rawMaterialSupplier = rawMaterialSupplierService.getRawMaterialSupplierById(supplierId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid supplier Id:" + supplierId));
		model.addAttribute("raw_material_supplier", rawMaterialSupplier); // 수정할 고객 정보 모델에 추가
		return "raw_material_supplier/edit"; // 고객 수정 페이지로 이동
	}

	// 고객 수정 처리 메서드
	@PostMapping("/table/raw_material_supplier/edit/{supplierId}")
	public String updateRaw_material_supplier(@PathVariable("supplierId") int supplierId, @ModelAttribute RawMaterialSupplier rawMaterialSupplier) {
		rawMaterialSupplierService.updateRawMaterialSupplier(supplierId, rawMaterialSupplier); // 고객 정보 수정
		return "redirect:/table/raw_material_supplier"; // 고객 목록 페이지로 리다이렉트
	}

	// 고객 삭제 처리 메서드
	@GetMapping("/table/raw_material_supplier/delete/{supplierId}")
	public String deleteRaw_material_supplier(@PathVariable("supplierId") int supplierId) {
		rawMaterialSupplierService.deleteRawMaterialSupplier(supplierId); // 고객 삭제
		return "redirect:/table/raw_material_supplier"; // 고객 목록 페이지로 리다이렉트
	}

}
