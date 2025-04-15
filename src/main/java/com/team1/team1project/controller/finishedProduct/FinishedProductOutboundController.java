package com.team1.team1project.controller.finishedProduct;

import com.team1.team1project.dto.*;
import com.team1.team1project.service.finishedProduct.FinishedProductInboundService;
import com.team1.team1project.service.finishedProduct.FinishedProductOutboundService;
import com.team1.team1project.service.productProcessManagement.ProductProcessManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/finished-product/outbound")
@RequiredArgsConstructor
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
    public ResponseEntity<PageResponseDTO<FinishedProductOutboundDTO>> getPagedList(@RequestBody PageRequestDTO pageRequestDTO) {
        log.info("📦 입고 리스트 요청: {}", pageRequestDTO);
        PageResponseDTO<FinishedProductOutboundDTO> response = finishedProductOutboundService.getPagedFinishedProductOutbounds(pageRequestDTO);
        return ResponseEntity.ok(response);
    }

    /** ✅ 3. 입고 상세 조회 */
    @GetMapping("/api/{outboundId}")
    @ResponseBody
    public ResponseEntity<FinishedProductOutboundDTO> getDetail(@PathVariable Long outboundId) {
        FinishedProductOutboundDTO dto = finishedProductOutboundService.getFinishedProductOutbound(outboundId);
        return ResponseEntity.ok(dto);
    }

    /** ✅ 4. 입고 등록 (POST JSON) */
    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody FinishedProductOutboundDTO dto) {
        finishedProductOutboundService.createFinishedProductOutbound(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("입고 등록 완료");
    }

    /** ✅ 5. 입고 등록 (폼 방식) */
    @PostMapping("/register")
    public String registerForm(@ModelAttribute FinishedProductOutboundDTO dto) {
        finishedProductOutboundService.createFinishedProductOutbound(dto);
        return "redirect:/finished-product/outbound/list";
    }

    /** ✅ 6. 입고 수정 (PUT JSON) */
    @PutMapping("/api/update/{outboundId}")
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable Long outboundId, @RequestBody FinishedProductOutboundDTO dto) {
        dto.setOutboundId(outboundId);
        finishedProductOutboundService.modifyOutboundStatus(outboundId, dto);
        return ResponseEntity.ok("입고 수정 완료");
    }

    /** ✅ 7. 상태만 수정 (PATCH JSON) */
    @PatchMapping("/api/status/{outboundId}")
    @ResponseBody
    public ResponseEntity<String> patchStatus(@PathVariable Long outboundId, @RequestBody Map<String, String> statusMap) {
        String status = statusMap.get("status");
        FinishedProductOutboundDTO dto = new FinishedProductOutboundDTO();
        dto.setStatus(status);
        finishedProductOutboundService.modifyOutboundStatus(outboundId, dto);
        return ResponseEntity.ok("상태 수정 완료");
    }

    /** ✅ 8. 입고 삭제 (DELETE) */
    @DeleteMapping("/api/delete/{outboundId}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long outboundId) {
        finishedProductOutboundService.deleteFinishedProductOutbound(outboundId);
        return ResponseEntity.ok("삭제 완료");
    }

    /** ✅ 9. 히스토리 페이지 뷰 */
    @GetMapping("/history")
    public String getHistoryPage(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<FinishedProductOutboundDTO> historyPage =
                finishedProductOutboundService.getFinishedProductOutboundHistoryForTable("historyId", false, pageRequestDTO);

        model.addAttribute("historyPage", historyPage);
        return "finishedProduct/historyList";
    }

    /** ✅ 10. 히스토리 API (POST JSON + 정렬) */
    @PostMapping("/api/history")
    @ResponseBody
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
