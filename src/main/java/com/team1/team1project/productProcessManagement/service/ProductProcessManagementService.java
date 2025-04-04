package com.team1.team1project.productProcessManagement.service;

import com.team1.team1project.domain.MachineGuiInfo;
import com.team1.team1project.dto.MachineGuiInfoDTO;
import com.team1.team1project.dto.MachineHistoryDTO;
import com.team1.team1project.dto.MachineRawMaterialConsumeDTO;
import com.team1.team1project.productProcessManagement.dto.*;

import java.util.List;

public interface ProductProcessManagementService {
    MachineHistoryYearDTO getMachineHistoryByYear(int year);
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
}
