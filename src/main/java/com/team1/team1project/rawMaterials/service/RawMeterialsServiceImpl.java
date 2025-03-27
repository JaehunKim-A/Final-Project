package com.team1.team1project.rawMaterials.service;


import com.team1.team1project.domain.RawMaterials;
import com.team1.team1project.dto.RawMaterialsDTO;
import com.team1.team1project.rawMaterials.repository.RawMaterialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class RawMeterialsServiceImpl implements RawMaterialsService{
    private final ModelMapper modelMapper;
    private final RawMaterialsRepository rawMaterialsRepository;

    @Override
    public Long registers(RawMaterialsDTO rawMaterialsDTO){
        String[] materialCodes = rawMaterialsDTO.getMaterialCode().split(",");
        String[] materialNames = rawMaterialsDTO.getMaterialName().split(",");
        String[] units = rawMaterialsDTO.getUnit().split(",");
        String[] descriptions = rawMaterialsDTO.getDescription().split(",");
        String[] categories = rawMaterialsDTO.getCategory().split(",");
        for(int i = 0; i < materialCodes.length; i++){
            String materialCode = materialCodes[i].trim();
            String materialName = materialNames[i].trim();
            String unit = units[i].trim();
            String description = descriptions[i].trim();
            String category = categories[i].trim();

            RawMaterials rawMaterials = RawMaterials.builder()
                    .materialCode(materialCode)
                    .materialName(materialName)
                    .unit(unit)
                    .description(description)
                    .category(category)
                    .build();
            RawMaterials saveRaw = rawMaterialsRepository.save(rawMaterials);
        }
        return 1L;
    }

    @Override
    public RawMaterialsDTO readOne(Long materialId){
        Optional<RawMaterials> readMaterials = rawMaterialsRepository.findById(materialId);
        RawMaterials rawMaterials = readMaterials.orElseThrow(() -> new RuntimeException("MatId"));
        RawMaterialsDTO rawMaterialsDTO = modelMapper.map(rawMaterials, RawMaterialsDTO.class);
        return rawMaterialsDTO;
    }
    @Override
    public void modifyOne(RawMaterialsDTO rawMaterialsDTO){
        Optional<RawMaterials> readMaterial = rawMaterialsRepository.findById(rawMaterialsDTO.getMaterialId());
        RawMaterials rawMaterials = readMaterial.orElseThrow(() -> new RuntimeException("MatId"));

        rawMaterials.rawMaterialsChange(
                rawMaterialsDTO.getMaterialCode(),
                rawMaterialsDTO.getMaterialName(),
                rawMaterialsDTO.getUnit(),
                rawMaterialsDTO.getDescription(),
                rawMaterialsDTO.getCategory()
        );
        rawMaterialsRepository.save(rawMaterials);
    }
    @Override
    public void removeOne(Long materialId){rawMaterialsRepository.deleteById(materialId);}

    @Override
    public List<RawMaterials> getAllMaterials(){
        return rawMaterialsRepository.findAll();
    }

}
