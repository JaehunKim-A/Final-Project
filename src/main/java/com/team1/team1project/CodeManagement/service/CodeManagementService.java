package com.team1.team1project.CodeManagement.service;

import com.team1.team1project.dto.CodeManagementDTO;

public interface CodeManagementService{
    Long registers(CodeManagementDTO codeManagementDTO);
    CodeManagementDTO readOne(Long codeId);
    void modifyOne(CodeManagementDTO codeManagementDTO);
    void removeOne(Long codeId);
}