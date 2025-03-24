package com.team1.team1project.service;

import com.team1.team1project.domain.FinishedProductInbound;
import com.team1.team1project.finishedproductinbound.repository.FinishedProductInboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinishedProductInboundService {

    private final FinishedProductInboundRepository repository;

    public List<FinishedProductInbound> getAllInbounds() {
        return repository.findAll();
    }
}
