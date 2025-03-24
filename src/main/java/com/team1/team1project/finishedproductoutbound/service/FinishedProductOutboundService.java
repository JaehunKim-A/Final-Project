package com.team1.team1project.service;

import com.team1.team1project.domain.FinishedProductOutbound;
import com.team1.team1project.repository.FinishedProductOutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FinishedProductOutboundService {

    private final FinishedProductOutboundRepository repository;

}
