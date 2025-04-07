package com.team1.team1project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MachineGuiInfo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer guiId;
    private String machineId;
    private int xCoordinate;
    private int yCoordinate;
    private String machineType;

    public void setGuiId(Integer guiId) {
        this.guiId = guiId;
    }
}
