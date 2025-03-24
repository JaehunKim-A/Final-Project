package com.team1.team1project.raw_material_suppliers.controller;

import com.team1.team1project.raw_material_suppliers.domain.RawMaterialSupplier;
import com.team1.team1project.raw_material_suppliers.service.RawMaterialSupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
	public String showRawMaterialSupplierList(Model model,
	                                          @RequestParam(value = "page", defaultValue = "1") int page,
	                                          @PageableDefault(size = 10) Pageable pageableRaw) {

		int pageIndex = page - 1; // 최소 0 이상이 되도록 처리
		Pageable pageable = PageRequest.of(pageIndex, pageableRaw.getPageSize(), pageableRaw.getSort());
		Page<RawMaterialSupplier> rawMaterialSupplierPage = rawMaterialSupplierService.getRawMaterialSupplierPage(pageable);

		model.addAttribute("page", rawMaterialSupplierPage);
		model.addAttribute("pageNumber", page); // 현재 페이지 (1부터 시작)
		model.addAttribute("suppliers", rawMaterialSupplierPage.getContent());

		List<String> columnNames = List.of(
				"supplierId", "supplierName", "contactInfo", "address", "email", "phone_number", "reg_date", "mod_date"
		);
		model.addAttribute("columns", columnNames);

		return "dist/raw_material_supplier/table";
	}



	// 고객 등록 폼을 표시하는 메서드
	@GetMapping("/table/raw_material_supplier/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("raw_material_supplier", new RawMaterialSupplier()); // 새 고객 객체를 모델에 추가
		return "dist/raw_material_supplier/register"; // 고객 등록 페이지로 이동
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
		return "dist/raw_material_supplier/edit"; // 고객 수정 페이지로 이동
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
