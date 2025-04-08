package com.team1.team1project.service.productProcessManagement;

import com.team1.team1project.dto.*;

import java.util.List;

public interface ProductProcessManagementService {
    MachineHistoryDaysDTO getProductionAmount2Week();
    List<FormattedMachineRawMaterialConsumeDTO> getMachineRawMaterialConsume();
    List<FormattedMachineRawMaterialReserveDTO> getMachineRawMaterialReserve();
    List<MachineHistoryDTO> getMachineHistoryToday();
    List<MachineGuiInformationsDTO> getMachineGuiInformations();
    boolean addGuiMachine(MachineGuiInfoDTO machine);
    List<String> getMachineIdList();
    boolean checkCoordinates(int xCoordinate, int yCoordinate, String machineId);
    List<String> getNotExistOnGuiMachineList();
    MachineGuiInfoDTO getMachineGuiInfo(String machineId);
    void modifyMachineGuiInfo(MachineGuiInfoDTO machineGuiInfoDTO);
    void deleteMachineGuiInfo(String machineId);
    List<ProcessBarChartDataDTO> getProductionTargetRatio();
    PageResponseDTO<MachineHistoryDTO> getMachineHistoryForTable(String sorter, boolean isAsc, PageRequestDTO pageRequestDTO);
}
