package com.team1.team1project.controller.finishedProduct;

import com.team1.team1project.domain.FinishedProductStock;
import com.team1.team1project.service.finishedProduct.FinishedProductStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/finishedProduct")
public class FinishedProductStockController {
    private final FinishedProductStockService finishedProductStockService;

    @GetMapping("/stock")
    public void list(Model model){
        List<FinishedProductStock> finishedProductStock = finishedProductStockService.getAllStock();
        List<String> columns = List.of("productCode", "stock");
        model.addAttribute("columns", columns);
        model.addAttribute("finishedProductStock", finishedProductStock);
    }
}
