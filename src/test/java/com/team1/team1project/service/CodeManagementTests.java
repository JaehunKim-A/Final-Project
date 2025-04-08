package com.team1.team1project.service;

import com.team1.team1project.service.codeManagement.CodeManagementService;
import com.team1.team1project.dto.CodeManagementDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class CodeManagementTests{

    @Autowired
    private CodeManagementService codeManagementService;

    @Test
    public void testRegister(){
        IntStream.rangeClosed(1,20).forEach(i ->{
            CodeManagementDTO codeManagementDTO = CodeManagementDTO.builder()
                    .codeType("완제품")
                    .codeName("iPhone" + i)
                    .codeDescription("iPhone")
                    .category("iPhone")
                    .registeredBy("tester")
                    .updatedBy("tester")
                    .codeValue("I1001" + i)
                    .build();
            Long codeId = codeManagementService.registers(codeManagementDTO);
        });
    }

    @Test
    public void modifyTest(){
        CodeManagementDTO codeManagementDTO = CodeManagementDTO.builder()
                .codeId(2L)
                .codeValue("T1001")
                .codeName("something")
                .codeDescription("testing")
                .category("아이우에오")
                .codeType("원자재")
                .build();
        codeManagementService.modifyOne(codeManagementDTO);
    }
}
