package com.team1.team1project.service;

import com.team1.team1project.codeManagement.service.CodeManagementService;
import com.team1.team1project.dto.CodeManagementDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class CodeManagementTests{

    @Autowired
    private CodeManagementService codeManagementService;

    @Test
    public void testRegister(){

        CodeManagementDTO codeManagementDTO = CodeManagementDTO.builder()
                .codeType("완제품")
                .codeName("iPhone1")
                .codeDescription("iPhone")
                .category("iPhone")
                .registeredBy("tester")
                .updatedBy("tester")
                .codeValue("I1001")
                .build();
        Long codeId = codeManagementService.registers(codeManagementDTO);

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
