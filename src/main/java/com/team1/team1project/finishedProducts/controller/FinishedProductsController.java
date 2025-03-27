package com.team1.team1project.finishedProducts.controller;

import com.team1.team1project.domain.FinishedProducts;
import com.team1.team1project.dto.FinishedProductsDTO;
import com.team1.team1project.finishedProducts.service.FinishedProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/final")
@RequiredArgsConstructor
public class FinishedProductsController {
    private final FinishedProductsService finishedProductsService;

    @GetMapping("/finishedProduct/list")
    public void list(Model model){
        List<FinishedProducts> finishedProducts = finishedProductsService.getAllProducts();
        List<String> columns = List.of("productId", "productCode", "description", "unit", "category");
        model.addAttribute("columns", columns);
        model.addAttribute("finishedProducts", finishedProducts);
    }
    @GetMapping("/finishedProduct/register")
    public void finishedProductsRegister(){}

    @PostMapping("/finishedProduct/register")
    public String registerPost(@Valid FinishedProductsDTO finishedProductsDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/final/finishedProduct/register";
        }
        Long productId = finishedProductsService.registers(finishedProductsDTO);
        redirectAttributes.addFlashAttribute("result", productId);
        return "redirect:/final/finishedProduct/list";
    }
    @GetMapping({"/finishedProduct/read", "/finishedProduct/modify"})
    public String read(Model model,
                       Long productId){
        FinishedProductsDTO finishedProductsDTO = finishedProductsService.readOne(productId);
        model.addAttribute("finishedProductsDTO", finishedProductsDTO);
        return "redirect:/final/finishedProduct/list";
    }
    @PostMapping("/finishedProduct/modify")
    public String modify(@Valid FinishedProductsDTO finishedProductsDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("productId", finishedProductsDTO.getProductId());
            return "redirect:/final/finishedProduct/list";
        }
        return "redirect:/final/finishedProduct/modify";
    }
    @PostMapping("/finishedProduct/remove")
    public String remove(RedirectAttributes redirectAttributes,
                         Long productId){
        finishedProductsService.readOne(productId);
        redirectAttributes.addFlashAttribute("result", "remove");
        return "redirect:/final/finishedProduct/list";
    }
}
