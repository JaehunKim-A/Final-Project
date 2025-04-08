package com.team1.team1project.mapper;

import com.team1.team1project.dto.FormattedMachineRawMaterialConsumeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MachineRawMaterialConsumeMapper {
    List<FormattedMachineRawMaterialConsumeDTO> selectFormattedMachineRawMaterialConsume();
}
