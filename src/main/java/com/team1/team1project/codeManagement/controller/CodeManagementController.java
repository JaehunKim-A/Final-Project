package com.team1.team1project.codeManagement.controller;

import com.team1.team1project.codeManagement.service.CodeManagementService;
import com.team1.team1project.domain.CodeManagement;
import com.team1.team1project.dto.CodeManagementDTO;
import com.team1.team1project.dto.PageRequestDTO;
import com.team1.team1project.dto.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RestController
@RequestMapping("/final")
@RequiredArgsConstructor
public class CodeManagementController {
    private final CodeManagementService codeManagementService;

    @GetMapping("/codeManagement")
    public void list(PageRequestDTO pageRequestDTO,
                     Model model){
//        PageResponseDTO<CodeManagementDTO> responseDTO = codeManagementService.list(pageRequestDTO);
//        model.addAttribute("responseDTO", responseDTO);
        model.addAttribute("pageRequestDTO", pageRequestDTO);
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
    public String read(PageRequestDTO pageRequestDTO,
                       Model model,
                       Long codeId){
        CodeManagementDTO codeManagementDTO = codeManagementService.readOne(codeId);
        model.addAttribute("codeManagementDto", codeManagementDTO);

        return "redirect:/final/codeManagement/list";
    }

    @PostMapping("/codeManagement/modify")
    public String Mmdify(@Valid CodeManagementDTO  codeManagementDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         PageRequestDTO pageRequestDTO){
        if(bindingResult.hasErrors()){
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("codeId", codeManagementDTO.getCodeId());
            return "redirect:/final/codeManagement/list?" + link;
        }
        return "/final/codeManagement/modify";
    }
    @PostMapping("/codeManagement/remove")
    public String remove(RedirectAttributes redirectAttributes,
                         PageRequestDTO pageRequestDTO,
                         Long codeId){
        String link = pageRequestDTO.getLink();
        codeManagementService.removeOne(codeId);
        redirectAttributes.addFlashAttribute("result", "removed");
        redirectAttributes.addFlashAttribute("link", link);
        return "redirect:/fianl/codeManagement/list";
    }
}
