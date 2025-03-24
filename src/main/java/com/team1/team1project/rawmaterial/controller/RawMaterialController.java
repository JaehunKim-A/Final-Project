package com.team1.team1project.rawmaterial.controller;

import com.team1.team1project.rawmaterial.service.RawMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/raw-materials")
@RequiredArgsConstructor
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

}
