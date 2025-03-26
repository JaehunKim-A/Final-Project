package com.team1.team1project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MachineRawMaterialReserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_id")
    private int reserveId;
    @Column(name = "machine_id")
    private String machineId;
    @Column(name = "material_id")
    private int materialId;
    private int stock;
}
