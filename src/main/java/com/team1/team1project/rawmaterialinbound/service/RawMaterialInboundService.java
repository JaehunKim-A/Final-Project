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

    // 원자재 입고 목록을 가져오는 메서드
    public List<RawMaterialInbound> getAllInbounds() {
        List<RawMaterialInbound> inbounds = repository.findAll();
        System.out.println("조회데이터:" + inbounds.size());
        inbounds.forEach(inbound -> System.out.println(inbound.toString()));
        // 데이터베이스에서 원자재 입고 데이터 모두 가져오기
        return inbounds;
    }
}
