package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Getter
public class MachineInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_id")
    private String machineId;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "machine_description")
    private String machineDescription;
}
