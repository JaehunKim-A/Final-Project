package com.team1.team1project.productProcessManagement.mapper;

import com.team1.team1project.productProcessManagement.dto.FormattedMachineRawMaterialReserveDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MachineRawMaterialReserveMapper {
    List<FormattedMachineRawMaterialReserveDTO> selectFormattedMachineRawMaterialReserve();
}
