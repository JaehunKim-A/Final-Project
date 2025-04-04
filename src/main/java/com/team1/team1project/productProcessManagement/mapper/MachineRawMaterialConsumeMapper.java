package com.team1.team1project.productProcessManagement.mapper;

import com.team1.team1project.productProcessManagement.dto.FormattedMachineRawMaterialConsumeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MachineRawMaterialConsumeMapper {
    List<FormattedMachineRawMaterialConsumeDTO> selectFormattedMachineRawMaterialConsume();
}
