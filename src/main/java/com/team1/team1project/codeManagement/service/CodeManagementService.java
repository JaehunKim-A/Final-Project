package com.team1.team1project.codeManagement.service;

import com.team1.team1project.domain.CodeManagement;
import com.team1.team1project.dto.CodeManagementDTO;;

import java.util.List;

public interface CodeManagementService{
    Long registers(CodeManagementDTO codeManagementDTO);
    CodeManagementDTO readOne(Long codeId);
    void modifyOne(CodeManagementDTO codeManagementDTO);
    void removeOne(Long codeId);
    List<CodeManagement> getAllCode();
}