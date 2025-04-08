package com.team1.team1project.productProcessManagement.controller;

import com.team1.team1project.dto.MachineGuiInfoDTO;
import com.team1.team1project.dto.MachineHistoryDTO;
import com.team1.team1project.dto.PageRequestDTO;
import com.team1.team1project.dto.PageResponseDTO;
import com.team1.team1project.productProcessManagement.dto.*;
import com.team1.team1project.productProcessManagement.service.ProductProcessManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequestMapping("/api/productProcessManagement")
@RequiredArgsConstructor
public class ProductProcessManagementRestController {

    private final ProductProcessManagementService productProcessManagementService;

    @GetMapping("/productProcessManagementGet")
    public void getMachineHistory(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<MachineHistoryDTO> machineHistoryDTOPageResponseDTO =
                productProcessManagementService.getMachineHistoryForTable("historyId", false, pageRequestDTO);

        model.addAttribute("machineHistoryYearDTO", machineHistoryDTOPageResponseDTO);
    }

    @PostMapping("/productProcessManagementPost")
    @ResponseBody
    public PageResponseDTO<MachineHistoryDTO> postMachineHistory(@RequestBody MachineHistoryRequestDTO request) {
        log.info(request.isAsc());

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(request.getPage())
                .size(request.getSize())
                .type(request.getType())
                .keyword(request.getKeyword())
                .build();

        PageResponseDTO<MachineHistoryDTO> result =
                productProcessManagementService.getMachineHistoryForTable(
                        request.getSorter(), request.isAsc(), pageRequestDTO);

        return result;
    }

    @GetMapping("/machineHistory/twoWeeks")
    public ResponseEntity<MachineHistoryDaysDTO> getProductionAmount2Week() {
        MachineHistoryDaysDTO machineHistoryDaysDTO =
                productProcessManagementService.getProductionAmount2Week();
        return ResponseEntity.ok(machineHistoryDaysDTO);
    }

    @GetMapping("/machineHistory/today")
    public ResponseEntity<List<MachineHistoryDTO>> getMachineHistoryToday() {
        List<MachineHistoryDTO> machineHistoryTodayDTOList =
                productProcessManagementService.getMachineHistoryToday();
        return ResponseEntity.ok(machineHistoryTodayDTOList);
    }

    @GetMapping("/rawMaterial/consume")
    public ResponseEntity<List<FormattedMachineRawMaterialConsumeDTO>> getMachineRawMaterialConsume() {
        List<FormattedMachineRawMaterialConsumeDTO> formattedMachineRawMaterialConsumeDTOList =
                productProcessManagementService.getMachineRawMaterialConsume();
        return ResponseEntity.ok(formattedMachineRawMaterialConsumeDTOList);
    }

    @GetMapping("/rawMaterial/reserve")
    public ResponseEntity<List<FormattedMachineRawMaterialReserveDTO>> getMachineRawMaterialReserve() {
        List<FormattedMachineRawMaterialReserveDTO> formattedMachineRawMaterialReserveDTOList =
                productProcessManagementService.getMachineRawMaterialReserve();
        return ResponseEntity.ok(formattedMachineRawMaterialReserveDTOList);
    }

    @GetMapping("/machine/guiInfo")
    public ResponseEntity<List<MachineGuiInformationsDTO>> getMachineGuiInformations() {
        List<MachineGuiInformationsDTO> machineGuiInfoDTOList =
                productProcessManagementService.getMachineGuiInformations();
        return ResponseEntity.ok(machineGuiInfoDTOList);
    }

    @GetMapping("/addMachine/machineList")
    public List<String> getNotExistOnGuiMachineList() {
        List<String> machineList =
                productProcessManagementService.getNotExistOnGuiMachineList();
        return machineList;
    }

    @GetMapping("/addMachine/checkCoordinate")
    public ResponseEntity<Map<String, Boolean>> checkCoordinate(
            @RequestParam("x") int x,
            @RequestParam("y") int y,
            @RequestParam("machineId") String machineId) {

        boolean isAvailable = productProcessManagementService.checkCoordinates(x, y, machineId);

        return ResponseEntity.ok(Map.of("available", isAvailable));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        Map<String, Object> dashboardData = new HashMap<>();

        // 2주간 생산량 데이터
        MachineHistoryDaysDTO machineHistoryDaysDTO =
                productProcessManagementService.getProductionAmount2Week();

        // 원자재 소비량 데이터
        List<FormattedMachineRawMaterialConsumeDTO> formattedMachineRawMaterialConsumeDTOList =
                productProcessManagementService.getMachineRawMaterialConsume();

        // 원자재 재고량 데이터
        List<FormattedMachineRawMaterialReserveDTO> formattedMachineRawMaterialReserveDTOList =
                productProcessManagementService.getMachineRawMaterialReserve();

        // 오늘의 기계 이력 데이터
        List<MachineHistoryDTO> machineHistoryTodayDTOList =
                productProcessManagementService.getMachineHistoryToday();

        // 기계 GUI 정보
        List<MachineGuiInformationsDTO> machineGuiInfoDTOList =
                productProcessManagementService.getMachineGuiInformations();

        // 생산량 차트 데이터 처리
        Map<String, List<Integer>> groupedProductionAmounts = machineHistoryDaysDTO.getDtoList().stream()
                .collect(Collectors.toMap(
                        data -> data.getMachineId(),
                        data -> data.getProductionAmountData(),
                        (list1, list2) -> list1
                ));

        // 원자재 소비량 데이터 처리
        Map<String, Integer> machineRawMaterialTotalConsume = formattedMachineRawMaterialConsumeDTOList.stream()
                .collect(Collectors.groupingBy(
                        FormattedMachineRawMaterialConsumeDTO::getMachineId,
                        Collectors.summingInt(FormattedMachineRawMaterialConsumeDTO::getQuantity)
                ));

        // 불량률 데이터 처리
        List<Map<String, Object>> productionDefectiveData = machineHistoryTodayDTOList.stream()
                .map(history -> {
                    Map<String, Object> pieChartData = new HashMap<>();

                    // 불량률 계산
                    double defectiveRate = history.getProductionAmount() > 0
                            ? (double) history.getDefectiveAmount() / history.getProductionAmount() * 100
                            : 0.0;

                    pieChartData.put("machineId", history.getMachineId());
                    pieChartData.put("defectiveRate", defectiveRate);
                    pieChartData.put("productionRate", 100 - defectiveRate);
                    pieChartData.put("defectiveAmount", history.getDefectiveAmount());
                    pieChartData.put("productionAmount", history.getProductionAmount());

                    return pieChartData;
                })
                .collect(Collectors.toList());

        // 대시보드 데이터에 추가
        dashboardData.put("machineHistoryDays", machineHistoryDaysDTO);
        dashboardData.put("daysList", machineHistoryDaysDTO.getDayList());
        dashboardData.put("productionAmounts", groupedProductionAmounts);
        dashboardData.put("rawMaterialConsume", machineRawMaterialTotalConsume);
        dashboardData.put("rawMaterialReserve", formattedMachineRawMaterialReserveDTOList);
        dashboardData.put("productionDefectiveData", productionDefectiveData);
        dashboardData.put("machineGuiInfo", machineGuiInfoDTOList);

        return ResponseEntity.ok(dashboardData);
    }

    @PostMapping("/addMachine/add")
    public ResponseEntity<String> addGuiMachine(@RequestBody MachineGuiInfoDTO machineGuiInfoDTO) {
        System.out.println("Received data: " + machineGuiInfoDTO); // 로그 확인용
        boolean success = productProcessManagementService.addGuiMachine(machineGuiInfoDTO);

        if (success) {
            return ResponseEntity.ok("Machine added successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to add machine (Duplicate Coordinates)");
        }
    }

    @GetMapping("/modifyMachine/{machineId}")
    public ResponseEntity<?> getMachineInfo(@PathVariable String machineId) {
        MachineGuiInfoDTO machineInfo = productProcessManagementService.getMachineGuiInfo(machineId);

        if (machineInfo != null) {
            return ResponseEntity.ok(machineInfo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("머신 정보를 찾을 수 없습니다.");
        }
    }

    @PostMapping("/modifyMachine/update")
    public ResponseEntity<String> updateMachineInfo(@RequestBody MachineGuiInfoDTO machineGuiInfoDTO) {
        try {
            productProcessManagementService.modifyMachineGuiInfo(machineGuiInfoDTO);
            return ResponseEntity.ok("머신 정보가 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("머신 정보 업데이트 실패: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteMachine/delete/{machineId}")
    public ResponseEntity<String> deleteMachineInfo(@PathVariable String machineId) {
        productProcessManagementService.deleteMachineGuiInfo(machineId);

        try {
            return ResponseEntity.ok("머신 정보가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("머신 정보 삭제 실패" + e.getMessage());
        }
    }

    @GetMapping("/machine/targetRatio")
    public ResponseEntity<List<ProcessBarChartDataDTO>> getProductionTargetRatio() {
        List<ProcessBarChartDataDTO> data = productProcessManagementService.getProductionTargetRatio();
        return ResponseEntity.ok(data);
    }

}