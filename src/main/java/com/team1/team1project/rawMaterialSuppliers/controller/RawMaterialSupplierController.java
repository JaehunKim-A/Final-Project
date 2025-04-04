package com.team1.team1project.rawMaterialSuppliers.controller;

import com.team1.team1project.dto.RawMaterialSupplierDTO;
import com.team1.team1project.rawMaterialSuppliers.service.RawMaterialSupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequestMapping("/table/rawMaterialSupplier")
@RequiredArgsConstructor
public class RawMaterialSupplierController {

	private final RawMaterialSupplierService rawMaterialSupplierService;

	@GetMapping({"", "/"})
	public String showRawMaterialSupplierList(Model model) {
		List<RawMaterialSupplierDTO> rawMaterialSuppliers = rawMaterialSupplierService.getAllRawMaterialSuppliers();

		List<String> columnNames = List.of(
				"supplierId", "supplierName", "contactInfo", "address","email", "phoneNumber", "regDate", "modDate"
		);

		model.addAttribute("suppliers", rawMaterialSuppliers);
		model.addAttribute("columns", columnNames);

		return "rawMaterialSupplier/table";
	}

	// 등록 폼
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("rawMaterialSupplier", new RawMaterialSupplierDTO());
		return "rawMaterialSupplier/register";
	}

	// 등록 처리
	@PostMapping("/register")
	public String registerRawMaterialSupplier(@ModelAttribute("rawMaterialSupplier") RawMaterialSupplierDTO rawMaterialSupplierDTO) {
		rawMaterialSupplierService.createRawMaterialSupplier(rawMaterialSupplierDTO);
		return "redirect:/table/rawMaterialSupplier";
	}

	// 수정 폼
	@GetMapping("/edit/{supplierId}")
	public String showEditForm(@PathVariable("supplierId") int supplierId, Model model) {
		RawMaterialSupplierDTO rawMaterialSupplierDTO = rawMaterialSupplierService.getRawMaterialSupplierById(supplierId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid supplier Id: " + supplierId));
		model.addAttribute("rawMaterialSupplier", rawMaterialSupplierDTO);
		return "rawMaterialSupplier/edit";
	}

	// 수정 처리
	@PostMapping("/edit/{supplierId}")
	public String updateRawMaterialSupplier(@PathVariable("supplierId") int supplierId,
	                                        @ModelAttribute("rawMaterialSupplier") RawMaterialSupplierDTO rawMaterialSupplierDTO) {
		rawMaterialSupplierService.updateRawMaterialSupplier(supplierId, rawMaterialSupplierDTO);
		return "redirect:/table/rawMaterialSupplier";
	}

	// 삭제 처리
	@GetMapping("/delete/{supplierId}")
	public String deleteRawMaterialSupplier(@PathVariable("supplierId") int supplierId) {
		rawMaterialSupplierService.deleteRawMaterialSupplier(supplierId);
		return "redirect:/table/rawMaterialSupplier";
	}
}
