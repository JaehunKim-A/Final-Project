package com.team1.team1project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MachineRawMaterialConsume {
    @Id
    @Column(name = "consume_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consumeId;
    @Column(name = "machine_id")
    private int materialId;
    private int quantity;
}
