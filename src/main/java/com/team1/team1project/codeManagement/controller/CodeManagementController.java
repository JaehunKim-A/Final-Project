package com.team1.team1project.codeManagement.controller;

import com.team1.team1project.codeManagement.service.CodeManagementService;
import com.team1.team1project.domain.CodeManagement;
import com.team1.team1project.dto.CodeManagementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/codeManagement")
@RequiredArgsConstructor
public class CodeManagementController {
    private final CodeManagementService codeManagementService;

    @GetMapping("/codeManagement")
    public void list(Model model){
        List<CodeManagement> codeManagement = codeManagementService.getAllCode();
        List<String> columns = List.of("codeId", "codeValue", "codeName", "codeDescription", "category", "codeType");
        model.addAttribute("columns", columns);
        model.addAttribute("codeManagement", codeManagement);
    }

    @GetMapping("/codeManagement/register")
    public String codeManagementRegisterGet(Model model){
        model.addAttribute("codeManagement", new CodeManagementDTO());
        return "/codeManagement/codeManagement/register";
    }

    @PostMapping("/codeManagement/register")
    public String registerPost(@ModelAttribute("codeManagement") CodeManagementDTO codeManagementDTO){
        codeManagementService.registers(codeManagementDTO);
        return "redirect:/codeManagement/codeManagement";

    }

    @GetMapping("/codeManagement/edit/{codeId}")
    public String modifyEdit(Model model,
                       @PathVariable("codeId") Long codeId){
        CodeManagementDTO codeManagementDTO = codeManagementService.readOne(codeId);
        model.addAttribute("codeManagementDTO", codeManagementDTO);

        return "redirect:/codeManagement/codeManagement/edit";
    }

    @PostMapping("/codeManagement/edit/{codeId}")
    public String modify(@PathVariable("codeId") Long codeId,
                         @ModelAttribute("codeManagement") CodeManagementDTO codeManagementDTO){
        codeManagementService.modifyOne(codeManagementDTO);
        return "/codeManagement/codeManagement";
    }
    @PostMapping("/codeManagement/delete/{codeId}")
    public String remove(@PathVariable("codeId") Long codeId){
        codeManagementService.removeOne(codeId);
        return "redirect:/codeManagement/codeManagement";
    }
}
