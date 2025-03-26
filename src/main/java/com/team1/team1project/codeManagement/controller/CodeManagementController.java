package com.team1.team1project.codeManagement.controller;

import com.team1.team1project.codeManagement.service.CodeManagementService;
import com.team1.team1project.domain.CodeManagement;
import com.team1.team1project.dto.CodeManagementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/final")
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
    public void codeManagementRegisterGet(){}

    @PostMapping("/codeManagement/register")
    public String registerPost(@Valid CodeManagementDTO codeManagementDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/final/codeManagement/register";
        }
        Long codeId = codeManagementService.registers(codeManagementDTO);
        redirectAttributes.addFlashAttribute("result", codeId);
        return "redirect:/final/codeManagement/list";

    }

    @GetMapping({"/codeManagement/read", "/codeManagement/modify"})
    public String read(Model model,
                       Long codeId){
        CodeManagementDTO codeManagementDTO = codeManagementService.readOne(codeId);
        model.addAttribute("codeManagementDto", codeManagementDTO);

        return "redirect:/final/codeManagement/list";
    }

    @PostMapping("/codeManagement/modify")
    public String modify(@Valid CodeManagementDTO  codeManagementDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("codeId", codeManagementDTO.getCodeId());
            return "redirect:/final/codeManagement/list" ;
        }
        return "/final/codeManagement/modify";
    }
    @PostMapping("/codeManagement/remove")
    public String remove(RedirectAttributes redirectAttributes,
                         Long codeId){
        codeManagementService.removeOne(codeId);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/final/codeManagement/list";
    }
}
