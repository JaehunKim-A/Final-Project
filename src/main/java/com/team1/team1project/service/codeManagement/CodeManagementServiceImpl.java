package com.team1.team1project.service.codeManagement;

import com.team1.team1project.repository.CodeManagementRepository;
import com.team1.team1project.domain.CodeManagement;
import com.team1.team1project.dto.CodeManagementDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class CodeManagementServiceImpl implements CodeManagementService {
    private final ModelMapper modelMapper;
    private final CodeManagementRepository codeManagementRepository;

    @Override
    public Long registers(CodeManagementDTO codeManagementDTO){
        String[] codeValues = codeManagementDTO.getCodeValue().split(",");
        String[] codeNames = codeManagementDTO.getCodeName().split(",");
        String[] descriptions = codeManagementDTO.getDescription().split(",");
        String[] codeCategories = codeManagementDTO.getCategory().split(",");
        String[] codeTypes = codeManagementDTO.getCodeType().split(",");
        for(int i = 0; i < codeValues.length; i++){
            String codeValue = codeValues[i].trim();
            String codeName = codeNames[i].trim();
            String description = descriptions[i].trim();
            String codeCategory = codeCategories[i].trim();
            String codeType = codeTypes[i].trim();

            CodeManagement codeManagement = CodeManagement.builder()
                    .codeValue(codeValue)
                    .codeName(codeName)
                    .codeType(codeType)
                    .category(codeCategory)
                    .description(description)
                    .build();
            CodeManagement saveCode = codeManagementRepository.save(codeManagement);
        }
        return 1L;
    }

    @Override
    public CodeManagementDTO readOne(Long codeId){
        Optional<CodeManagement> readCode = codeManagementRepository.findById(codeId);
        CodeManagement codeManagement = readCode.orElseThrow(() -> new RuntimeException("Code Not Found"));
        CodeManagementDTO codeManagementDTO = modelMapper.map(codeManagement, CodeManagementDTO.class);

        return codeManagementDTO;
    }

    @Override
    public void modifyOne(CodeManagementDTO codeManagementDTO){
        Optional<CodeManagement> readCode = codeManagementRepository.findById(codeManagementDTO.getCodeId());
        CodeManagement codeManagement = readCode.orElseThrow(() -> new RuntimeException("Code Not Found"));

        codeManagement.codeChange(
                codeManagementDTO.getCodeValue(),
                codeManagementDTO.getCodeName(),
                codeManagementDTO.getCodeType(),
                codeManagementDTO.getCategory(),
                codeManagementDTO.getDescription()
        );

        codeManagementRepository.save(codeManagement);
    }

    @Override
    public void removeOne(Long codeId){codeManagementRepository.deleteById(codeId);}

    @Override
    public List<CodeManagement> getAllCode(){
        return codeManagementRepository.findAll();
    }
}
