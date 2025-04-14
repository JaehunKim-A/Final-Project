package com.team1.team1project.productProcessManagement.mapper;

import com.team1.team1project.dto.ProcessBarChartDataDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MachineInfoMapper {
    List<String> getMachineIdAll();
    List<ProcessBarChartDataDTO> selectProductionTargetRatio();
}
