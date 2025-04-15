package com.team1.team1project.controller.rawMaterial;

import com.team1.team1project.service.rawMaterial.RawMaterialSupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/table/rawMaterialSupplier")
public class RawMaterialSupplierController {

	private final RawMaterialSupplierService rawMaterialSupplierService;

	@GetMapping({"", "/"})
	public String showSupplierTable(Model model) {
		model.addAttribute("suppliers", rawMaterialSupplierService.getAllRawMaterialSuppliers());
		return "rawMaterialSupplier/table";
	}
}
