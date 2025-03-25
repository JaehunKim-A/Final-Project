package com.team1.team1project.rawMaterial.service;

import com.team1.team1project.dto.RawMaterialsDTO;
import com.team1.team1project.rawMaterial.repository.RawMaterialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class RawMeterialsService implements RawMaterialsService {
    private final ModelMapper modelMapper;
    private final RawMaterialRepository rawMaterialRepository;

    @Override
    public Long registers(RawMaterialsDTO rawMaterialsDTO){
        String[] codeValues = rawMaterialsDTO.

    }
}
