package com.team1.team1project.productProcessManagement.mapper;

import com.team1.team1project.domain.MachineGuiInfo;
import com.team1.team1project.productProcessManagement.dto.MachineGuiInformationsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MachineGuiInfoMapper {
    List<MachineGuiInformationsDTO> selectMachineGuiInformations();
    void insertMachineGuiInfo(MachineGuiInfo machineGuiInfo);
    void modifyMachineGuiInfo(MachineGuiInfo machineGuiInfo);
    void deleteMachineGuiInfo(String machineId);
    int checkCoordinates(int xCoordinate, int yCoordinate);
    List<String> selectNotExistOnGuiMachineList();
    int checkCoordinates(int xCoordinate, int yCoordinate, String line);
}
