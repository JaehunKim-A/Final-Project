package com.team1.team1project.productProcessManagement.service;

import com.team1.team1project.domain.MachineGuiInfo;
import com.team1.team1project.domain.MachineHistory;
import com.team1.team1project.dto.MachineGuiInfoDTO;
import com.team1.team1project.dto.MachineHistoryDTO;
import com.team1.team1project.dto.PageRequestDTO;
import com.team1.team1project.dto.PageResponseDTO;
import com.team1.team1project.productProcessManagement.dto.*;
import com.team1.team1project.productProcessManagement.mapper.*;
import com.team1.team1project.productProcessManagement.repository.MachineGuiInfoRepository;
import com.team1.team1project.productProcessManagement.repository.MachineHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductProcessManagementServiceImpl implements ProductProcessManagementService{
    private final ModelMapper modelMapper;

    private final MachineHistoryRepository machineHistoryRepository;
    private final MachineGuiInfoRepository machineGuiInfoRepository;
    private final MachineHistoryMapper machineHistoryMapper;
    private final MachineRawMaterialConsumeMapper machineRawMaterialConsumeMapper;
    private final MachineRawMaterialReserveMapper machineRawMaterialReserveMapper;
    private final MachineGuiInfoMapper machineGuiInfoMapper;
    private final MachineInfoMapper machineInfoMapper;

    @Override
    public MachineHistoryDaysDTO getProductionAmount2Week() {
        List<ProductionDataDTO> dtoList = new ArrayList<>();

        List<String> machines = machineHistoryRepository.findAll()
                .stream()
                .map(MachineHistory::getMachineId)
                .collect(Collectors.toList());

        for (String machineId : machines) {
            List<Integer> productionAmounts = machineHistoryMapper.selectProductAmount2Week(machineId);

            ProductionDataDTO productionDataDTO = new ProductionDataDTO(productionAmounts, machineId);
            dtoList.add(productionDataDTO);
        }

        List<Date> dayList = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 13; i >= 0; i--) {  // 2주 전부터 오늘까지 14일 동안 반복
            LocalDate date = today.minusDays(i);
            dayList.add(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }

        return MachineHistoryDaysDTO.builder()
                .dtoList(dtoList)
                .dayList(dayList)
                .build();
    }

    @Override
    public List<FormattedMachineRawMaterialConsumeDTO> getMachineRawMaterialConsume() {
        List<FormattedMachineRawMaterialConsumeDTO> machineRawMaterialConsumeDTOList = machineRawMaterialConsumeMapper.selectFormattedMachineRawMaterialConsume();

        return machineRawMaterialConsumeDTOList;
    }

    @Override
    public List<FormattedMachineRawMaterialReserveDTO> getMachineRawMaterialReserve() {
        List<FormattedMachineRawMaterialReserveDTO> machineRawMaterialReserveDTOList = machineRawMaterialReserveMapper.selectFormattedMachineRawMaterialReserve();

        return machineRawMaterialReserveDTOList;
    }

    @Override
    public List<MachineHistoryDTO> getMachineHistoryToday() {
        List<MachineHistoryDTO> dtoList = machineHistoryMapper.selectMachineHistoryToday().stream()
                .map(machineHistory -> modelMapper.map(machineHistory, MachineHistoryDTO.class)).collect(Collectors.toList());

        return dtoList;
    }

    @Override
    public List<MachineGuiInformationsDTO> getMachineGuiInformations() {
        return machineGuiInfoMapper.selectMachineGuiInformations();
    }

    @Override
    public List<String> getMachineIdList() {
        return machineInfoMapper.getMachineIdAll();
    }

    @Override
    public boolean checkCoordinates(int xCoordinate, int yCoordinate, String machineId) {
        // machine_id에서 라인 추출 (예: 'L1_P1' -> 'L1')
        String line = machineId.split("_")[0];
        return machineGuiInfoMapper.checkCoordinates(xCoordinate, yCoordinate, line) == 0;
    }

    @Override
    public boolean addGuiMachine(MachineGuiInfoDTO machine) {
        boolean isAvailable = checkCoordinates(machine.getXCoordinate(), machine.getYCoordinate(), machine.getMachineId());

        log.info("test message");

        if (!isAvailable) {
            return false; // 중복이면 추가하지 않음
        }

        // ModelMapper 대신 생성자를 사용하여 Entity 생성
        MachineGuiInfo machineEntity = new MachineGuiInfo(
            null, // guiId는 자동 생성
            machine.getMachineId(),
            machine.getXCoordinate(),
            machine.getYCoordinate(),
            machine.getMachineType()
        );

        machineGuiInfoMapper.insertMachineGuiInfo(machineEntity);
        return true;
    }

    @Override
    public MachineGuiInfoDTO getMachineGuiInfo(String machineId) {
        Optional<MachineGuiInfo> machineGuiInfo =
                machineGuiInfoRepository.findByMachineId(machineId);

        // 조회 결과가 있으면 DTO로 변환하여 반환
        if (machineGuiInfo.isPresent()) {
            MachineGuiInfo entity = machineGuiInfo.get();

            return MachineGuiInfoDTO.builder()
                    .guiId(entity.getGuiId())
                    .machineId(entity.getMachineId())
                    .xCoordinate(entity.getXCoordinate())
                    .yCoordinate(entity.getYCoordinate())
                    .machineType(entity.getMachineType())
                    .build();
        }

        return null;
    }

    @Override
    public void modifyMachineGuiInfo(MachineGuiInfoDTO machineGuiInfoDTO) {
        MachineGuiInfo machineGuiInfo =
                modelMapper.map(machineGuiInfoDTO, MachineGuiInfo.class);

        machineGuiInfoMapper.modifyMachineGuiInfo(machineGuiInfo);
    }

    @Override
    public List<String> getNotExistOnGuiMachineList() {
        return machineGuiInfoMapper.selectNotExistOnGuiMachineList();
    }

    @Override
    public void deleteMachineGuiInfo(String machineId) {
        try {
            machineGuiInfoMapper.deleteMachineGuiInfo(machineId);
        } catch (Exception e) {
            log.error("머신 GUI 정보 삭제 중 오류 발생: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ProcessBarChartDataDTO> getProductionTargetRatio() {
        return machineInfoMapper.selectProductionTargetRatio();
    }

    @Override
    public PageResponseDTO<MachineHistoryDTO> getMachineHistoryForTable(String sorter, boolean isAsc, PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable(isAsc, sorter);

        Page<MachineHistory> result = machineHistoryRepository.searchAll(types, keyword, pageable);

        List<MachineHistoryDTO> machineHistoryDTOList = result.getContent().stream()
                .map(machineHistory -> modelMapper.map(machineHistory, MachineHistoryDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<MachineHistoryDTO>builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(machineHistoryDTOList)
                .total((int) result.getTotalElements())
                .build();
    }
}
