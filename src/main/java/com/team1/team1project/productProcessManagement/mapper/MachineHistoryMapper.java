package com.team1.team1project.productProcessManagement.mapper;

import com.team1.team1project.domain.MachineHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface MachineHistoryMapper {
    List<MachineHistory> selectMachineHistoryByYear(@Param("year")int year);
    List<Integer> selectProductAmount2Week(String machineId);
    List<MachineHistory> selectMachineHistoryToday();
}
