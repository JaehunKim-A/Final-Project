package com.team1.team1project.controller.finishedProduct;

import com.team1.team1project.dto.*;
import com.team1.team1project.service.finishedProduct.FinishedProductInboundService;
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
@RequestMapping("/finished-product/inbound")
@RequiredArgsConstructor
public class FinishedProductInboundController {

    private final FinishedProductInboundService finishedProductInboundService;
    private final ProductProcessManagementService productProcessManagementService;

    /** ✅ 1. 페이지 뷰 */
    @GetMapping("/list")
    public String getListPage() {
        return "FinishedProductInbound";
    }

    /** ✅ 2. 페이지 + 검색 API (POST + JSON) */
    @PostMapping("/api/list")
    @ResponseBody
    public ResponseEntity<PageResponseDTO<FinishedProductInboundDTO>> getPagedList(@RequestBody PageRequestDTO pageRequestDTO) {
        log.info("📦 입고 리스트 요청: {}", pageRequestDTO);
        PageResponseDTO<FinishedProductInboundDTO> response = finishedProductInboundService.getPagedFinishedProductInbounds(pageRequestDTO);
        return ResponseEntity.ok(response);
    }

    /** ✅ 3. 입고 상세 조회 */
    @GetMapping("/api/{inboundId}")
    @ResponseBody
    public ResponseEntity<FinishedProductInboundDTO> getDetail(@PathVariable Long inboundId) {
        FinishedProductInboundDTO dto = finishedProductInboundService.getFinishedProductInbound(inboundId);
        return ResponseEntity.ok(dto);
    }

    /** ✅ 4. 입고 등록 (POST JSON) */
    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody FinishedProductInboundDTO dto) {
        finishedProductInboundService.createFinishedProductInbound(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("입고 등록 완료");
    }

    /** ✅ 5. 입고 등록 (폼 방식) */
    @PostMapping("/register")
    public String registerForm(@ModelAttribute FinishedProductInboundDTO dto) {
        finishedProductInboundService.createFinishedProductInbound(dto);
        return "redirect:/finished-product/inbound/list";
    }

    /** ✅ 6. 입고 수정 (PUT JSON) */
    @PutMapping("/api/update/{inboundId}")
    @ResponseBody
    public ResponseEntity<String> update(@PathVariable Long inboundId, @RequestBody FinishedProductInboundDTO dto) {
        dto.setInboundId(inboundId);
        finishedProductInboundService.modifyInboundStatus(inboundId, dto);
        return ResponseEntity.ok("입고 수정 완료");
    }

    /** ✅ 7. 상태만 수정 (PATCH JSON) */
    @PatchMapping("/api/status/{inboundId}")
    @ResponseBody
    public ResponseEntity<String> patchStatus(@PathVariable Long inboundId, @RequestBody Map<String, String> statusMap) {
        String status = statusMap.get("status");
        FinishedProductInboundDTO dto = new FinishedProductInboundDTO();
        dto.setStatus(status);
        finishedProductInboundService.modifyInboundStatus(inboundId, dto);
        return ResponseEntity.ok("상태 수정 완료");
    }

    /** ✅ 8. 입고 삭제 (DELETE) */
    @DeleteMapping("/api/delete/{inboundId}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long inboundId) {
        finishedProductInboundService.deleteFinishedProductInbound(inboundId);
        return ResponseEntity.ok("삭제 완료");
    }

    /** ✅ 9. 히스토리 페이지 뷰 */
    @GetMapping("/history")
    public String getHistoryPage(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<FinishedProductInboundDTO> historyPage =
                finishedProductInboundService.getFinishedProductInboundHistoryForTable("historyId", false, pageRequestDTO);

        model.addAttribute("historyPage", historyPage);
        return "finishedProduct/historyList";
    }

    /** ✅ 10. 히스토리 API (POST JSON + 정렬) */
    @PostMapping("/api/history")
    @ResponseBody
    public ResponseEntity<PageResponseDTO<FinishedProductInboundDTO>> getHistoryList(@RequestBody MachineHistoryRequestDTO request) {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(request.getPage())
                .size(request.getSize())
                .type(request.getType())
                .keyword(request.getKeyword())
                .build();

        PageResponseDTO<FinishedProductInboundDTO> result =
                finishedProductInboundService.getFinishedProductInboundHistoryForTable(
                        request.getSorter(), request.isAsc(), pageRequestDTO);

        return ResponseEntity.ok(result);
    }
}
