package com.team1.team1project.rawMaterials.controller;

import com.team1.team1project.domain.RawMaterials;
import com.team1.team1project.dto.RawMaterialsDTO;
import com.team1.team1project.rawMaterials.repository.RawMaterialsRepository;
import com.team1.team1project.rawMaterials.service.RawMaterialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rawMaterial")
public class RawMaterialsController {
    private final RawMaterialsService rawMaterialsService;

    @GetMapping("/rawMaterial")
    public void list(Model model){
        List<RawMaterials> rawMaterials = rawMaterialsService.getAllMaterials();
        List<String> columns = List.of("materialId", "materialCode", "unit", "category", "description");
        model.addAttribute("columns", columns);
        model.addAttribute("rawMaterials", rawMaterials);
    }
    @GetMapping("/rawMaterial/register")
    public String rawMaterialRegister(Model model){
        model.addAttribute("rawMaterials", new RawMaterialsDTO());
        return "/rawMaterial/rawMaterial/register";
    }

    @PostMapping("/rawMaterial/register")
    public String registerPost(@ModelAttribute("rawMaterials") RawMaterialsDTO rawMaterialsDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        rawMaterialsService.registers(rawMaterialsDTO);
        return "redirect:/rawMaterial/rawMaterial";

    }
    @GetMapping("/rawMaterial/edit/{materialId}")
    public String modifyEdit(@PathVariable("materialId") Long materialId,
                         Model model){
        RawMaterialsDTO rawMaterialsDTO = rawMaterialsService.readOne(materialId);
        model.addAttribute("rawMaterials", rawMaterialsDTO);
        return "/rawMaterial/rawMaterial/edit";
    }
    @PostMapping("/rawMaterial/edit/{materialId}")
    public String modify(@PathVariable("materialId") Long materialId,
                         @ModelAttribute("rawMaterials") RawMaterialsDTO rawMaterialsDTO){
        rawMaterialsService.modifyOne(rawMaterialsDTO);
        return "redirect:/rawMaterial/rawMaterial";
    }
    @GetMapping("/rawMaterial/delete/{materialId}")
    public String remove(@PathVariable("materialId") Long materialId){
        rawMaterialsService.removeOne(materialId);
        return "redirect:/rawMaterial/rawMaterial";
    }
}
