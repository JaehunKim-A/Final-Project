package com.team1.team1project.service;

import com.team1.team1project.domain.RawMaterialInbound;
import com.team1.team1project.repository.RawMaterialInboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RawMaterialInboundService {

    private final RawMaterialInboundRepository repository;

}
