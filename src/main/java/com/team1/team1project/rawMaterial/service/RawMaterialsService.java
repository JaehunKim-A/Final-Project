package com.team1.team1project.rawMaterial.service;

import com.team1.team1project.dto.CodeManagementDTO;

public interface RawMaterialsService {
    Long registers(CodeManagementDTO codeManagementDTO);
    CodeManagementDTO readOne(Long codeId);
    void modifyOne(CodeManagementDTO codeManagementDTO);
    void removeOne(Long codeId);
}
