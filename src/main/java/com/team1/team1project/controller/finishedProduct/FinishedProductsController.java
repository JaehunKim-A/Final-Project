package com.team1.team1project.controller.finishedProduct;

import com.team1.team1project.domain.FinishedProducts;
import com.team1.team1project.dto.FinishedProductsDTO;
import com.team1.team1project.service.finishedProduct.FinishedProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/finishedProduct")
@RequiredArgsConstructor
public class FinishedProductsController {
    private final FinishedProductsService finishedProductsService;

    @GetMapping("/finishedProduct")
    public void list(Model model){
        List<FinishedProducts> finishedProducts = finishedProductsService.getAllProducts();
        List<String> columns = List.of("productCode","productName", "unit", "category", "status", "description");
        model.addAttribute("columns", columns);
        model.addAttribute("finishedProducts", finishedProducts);
    }
    @GetMapping("/finishedProduct/register")
    public String finishedProductsRegister(Model model){
        model.addAttribute("finishedProducts", new FinishedProductsDTO());
        return "/finishedProduct/finishedProduct/register";
    }

    @PostMapping("/finishedProduct/register")
    public String registerPost(@ModelAttribute("finishedProducts") FinishedProductsDTO finishedProductsDTO){
        finishedProductsService.registers(finishedProductsDTO);
        return "redirect:/finishedProduct/finishedProduct";
    }
    @GetMapping("/finishedProduct/edit/{productId}")
    public String modifyEdit(@PathVariable("productId") Long productId,
                             Model model){
        FinishedProductsDTO finishedProductsDTO = finishedProductsService.readOne(productId);
        model.addAttribute("finishedProducts", finishedProductsDTO);
        return "redirect:/finishedProduct/finishedProduct/edit";
    }
    @PostMapping("/finishedProduct/edit/{productId}")
    public String modify(@PathVariable("productId") Long productId,
                         @ModelAttribute("finishedProducts") FinishedProductsDTO finishedProductsDTO){
        finishedProductsService.modifyOne(finishedProductsDTO);
        return "redirect:/finishedProduct/finishedProduct";
    }
    @GetMapping("/finishedProduct/delete/{productId}")
    public String remove(@PathVariable("productId") Long productId ){
        finishedProductsService.removeOne(productId);
        return "redirect:/finishedProduct/finishedProduct";
    }
}
