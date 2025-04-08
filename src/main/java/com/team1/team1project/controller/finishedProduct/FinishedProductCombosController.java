package com.team1.team1project.controller.finishedProduct;

import com.team1.team1project.domain.FinishedProductCombos;
import com.team1.team1project.service.finishedProduct.FinishedProductCombosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/combos")
public class FinishedProductCombosController {

    private final FinishedProductCombosService service;

    @Autowired
    public FinishedProductCombosController(FinishedProductCombosService service) {
        this.service = service;
    }

    // 전체 콤보 목록 조회
    @GetMapping
    public ResponseEntity<List<FinishedProductCombos>> getAllCombos() {
        List<FinishedProductCombos> combos = service.findAll();
        return new ResponseEntity<>(combos, HttpStatus.OK);
    }

    // 특정 ID의 콤보 조회
    @GetMapping("/{id}")
    public ResponseEntity<FinishedProductCombos> getComboById(@PathVariable Long id) {
        Optional<FinishedProductCombos> combo = service.findById(id);
        return combo.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 새로운 콤보 생성
    @PostMapping
    public ResponseEntity<FinishedProductCombos> createCombo(@RequestBody FinishedProductCombos combo) {
        FinishedProductCombos savedCombo = service.save(combo);
        return new ResponseEntity<>(savedCombo, HttpStatus.CREATED);
    }

    // 기존 콤보 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<FinishedProductCombos> updateCombo(@PathVariable Long id, @RequestBody FinishedProductCombos combo) {
        Optional<FinishedProductCombos> existingCombo = service.findById(id);
        if (existingCombo.isPresent()) {
            combo.setComboId(id);
            FinishedProductCombos updatedCombo = service.save(combo);
            return new ResponseEntity<>(updatedCombo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 콤보 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCombo(@PathVariable Long id) {
        Optional<FinishedProductCombos> existingCombo = service.findById(id);
        if (existingCombo.isPresent()) {
            service.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}