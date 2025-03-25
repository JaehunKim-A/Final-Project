package com.team1.team1project.finishedProductOutbound.service;

import com.team1.team1project.domain.FinishedProductOutbound;
import com.team1.team1project.repository.FinishedProductOutboundRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinishedProductOutboundService {

    private final FinishedProductOutboundRepository repository;

    public FinishedProductOutboundService(FinishedProductOutboundRepository repository) {
        this.repository = repository;
    }

    public List<FinishedProductOutbound> getAllOutbounds() {
        return repository.findAll();
    }
}
