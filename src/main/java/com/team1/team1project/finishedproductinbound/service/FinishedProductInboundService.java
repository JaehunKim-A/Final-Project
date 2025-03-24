package com.team1.team1project.finishedproductinbound.service;

import com.team1.team1project.domain.FinishedProductInbound;
import com.team1.team1project.finishedproductinbound.repository.FinishedProductInboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FinishedProductInboundService {

    private final FinishedProductInboundRepository finishedProductInboundRepository;

}
