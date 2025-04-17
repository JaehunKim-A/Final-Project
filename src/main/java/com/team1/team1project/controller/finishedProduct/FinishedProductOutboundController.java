package com.team1.team1project.controller.finishedProduct;

import com.team1.team1project.dto.FinishedProductOutboundDTO;
import com.team1.team1project.dto.MachineHistoryRequestDTO;
import com.team1.team1project.dto.PageRequestDTO;
import com.team1.team1project.dto.PageResponseDTO;
import com.team1.team1project.service.finishedProduct.FinishedProductOutboundService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/finished-product/outbound")
@RequiredArgsConstructor
@Log4j2
public class FinishedProductOutboundController {

    private final FinishedProductOutboundService finishedProductOutboundService;

    /** ✅ 1. 페이지 뷰 */
    @GetMapping("/list")
    public String getListPage() {
        return "FinishedProductOutbound";
    }

    /** ✅ 2. 페이지 + 검색 API (POST + JSON) */
    @PostMapping("/api/list")
    @ResponseBody
    public PageResponseDTO<FinishedProductOutboundDTO> getList(@RequestBody PageRequestDTO requestDTO) {
        return finishedProductOutboundService.getPagedFinishedProductOutbounds(requestDTO);
    }

    /** 3. 출고 상세 조회 (GET) */
    @GetMapping("/api/{outboundId}")
    public ResponseEntity<FinishedProductOutboundDTO> getDetail(@PathVariable Long outboundId) {
        FinishedProductOutboundDTO dto = finishedProductOutboundService.getFinishedProductOutbound(outboundId);
        return ResponseEntity.ok(dto);
    }

    /** 4. 출고 등록 (POST JSON) */
    @PostMapping("/api/register")
    public ResponseEntity<String> register(@RequestBody FinishedProductOutboundDTO dto) {
        finishedProductOutboundService.createFinishedProductOutbound(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("출고 등록 완료");
    }

    /** 5. 출고 수정 및 상태 수정 (PUT JSON) */
    @PutMapping("/api/update/{outboundId}")
    public ResponseEntity<String> update(@PathVariable Long outboundId, @RequestBody FinishedProductOutboundDTO dto) {
        dto.setOutboundId(outboundId);
        finishedProductOutboundService.modifyOutboundStatus(outboundId, dto);
        return ResponseEntity.ok("출고 수정 완료");
    }

    /** 7. 출고 삭제 (DELETE) */
    @DeleteMapping("/api/delete/{outboundId}")
    public ResponseEntity<String> delete(@PathVariable Long outboundId) {
        finishedProductOutboundService.deleteFinishedProductOutbound(outboundId);
        return ResponseEntity.ok("삭제 완료");
    }

    /** 8. 히스토리 페이지 API (POST JSON + 정렬) */
    @PostMapping("/api/history")
    public ResponseEntity<PageResponseDTO<FinishedProductOutboundDTO>> getHistoryList(@RequestBody MachineHistoryRequestDTO request) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(request.getPage())
                .size(request.getSize())
                .type(request.getType())
                .keyword(request.getKeyword())
                .build();

        PageResponseDTO<FinishedProductOutboundDTO> result =
                finishedProductOutboundService.getFinishedProductOutboundHistoryForTable(
                        request.getSorter(), request.isAsc(), pageRequestDTO);

        return ResponseEntity.ok(result);
    }
}
