package com.team1.team1project.controller.rawMaterial;

import com.team1.team1project.domain.RawMaterialStock;
import com.team1.team1project.service.rawMaterial.RawMaterialStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rawMaterial")
public class RawMaterialStockController {
    private final RawMaterialStockService rawMaterialStockService;

    @GetMapping("/stock")
    public void list(Model model){
        List<RawMaterialStock> rawMaterialStock = rawMaterialStockService.getAllStock();
        List<String> columns = List.of("materialCode", "stock");
        model.addAttribute("columns", columns);
        model.addAttribute("rawMaterialStock", rawMaterialStock);
    }

}
