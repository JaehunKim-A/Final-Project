package com.team1.team1project.codeManagement.service;

import com.team1.team1project.dto.CodeManagementDTO;
import com.team1.team1project.dto.PageRequestDTO;
import com.team1.team1project.dto.PageResponseDTO;

import java.util.List;

public interface CodeManagementService{
    Long registers(CodeManagementDTO codeManagementDTO);
    CodeManagementDTO readOne(Long codeId);
    void modifyOne(CodeManagementDTO codeManagementDTO);
    void removeOne(Long codeId);
//    PageResponseDTO<CodeManagementDTO> list(PageRequestDTO pageRequestDTO);
}