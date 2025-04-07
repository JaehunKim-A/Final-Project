package com.team1.team1project.productProcessManagement.repository;

import com.team1.team1project.domain.MachineGuiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MachineGuiInfoRepository extends JpaRepository<MachineGuiInfo, Integer> {
    @Query("SELECT m FROM MachineGuiInfo m WHERE m.machineId = :machineId")
    Optional<MachineGuiInfo> findByMachineId(@Param("machineId") String machineId);


}
