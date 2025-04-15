package com.team1.team1project.controller.codeManamgement;

import com.team1.team1project.service.codeManagement.CodeManagementService;
import com.team1.team1project.domain.CodeManagement;
import com.team1.team1project.dto.CodeManagementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/codeManagement")
@RequiredArgsConstructor
public class CodeManagementController {
    private final CodeManagementService codeManagementService;

    @GetMapping("/codeManagement")
    public void list(Model model){
        List<CodeManagement> codeManagement = codeManagementService.getAllCode();
        List<String> columns = List.of("CodeValue", "CodeName", "CodeType", "Category", "Description");
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
    @GetMapping("/codeManagement/delete/{codeId}")
    public String remove(@PathVariable("codeId") Long codeId){
        codeManagementService.removeOne(codeId);
        return "redirect:/codeManagement/codeManagement";
    }
}
