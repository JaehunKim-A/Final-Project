package com.team1.team1project.service;

import com.team1.team1project.dto.FinishedProductsDTO;
import com.team1.team1project.finishedproducts.service.FinishedProductsService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class FinishedProductsTests {
    @Autowired
    private FinishedProductsService finishedProductsService;

    @Test
    public void testRegister(){
        FinishedProductsDTO finishedProductsDTO = FinishedProductsDTO.builder()
                .codeId(1001)
                .productDescription("test")
                .status("Active")
                .build();

    }
}
