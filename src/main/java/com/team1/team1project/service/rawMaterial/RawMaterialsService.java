package com.team1.team1project.service.rawMaterial;

import com.team1.team1project.domain.RawMaterials;
import com.team1.team1project.dto.RawMaterialsDTO;

import java.util.List;

public interface RawMaterialsService {
    Long registers(RawMaterialsDTO rawMaterialsDTO);
    RawMaterialsDTO readOne(Long materialId);
    void modifyOne(RawMaterialsDTO rawMaterialsDTO);
    void removeOne(Long materialId);
    List<RawMaterials> getAllMaterials();
}
