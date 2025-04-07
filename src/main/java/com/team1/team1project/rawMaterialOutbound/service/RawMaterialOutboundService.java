package com.team1.team1project.rawMaterialOutbound.service;

import com.team1.team1project.domain.RawMaterialOutbound;
import com.team1.team1project.rawMaterialOutbound.repository.RawMaterialOutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RawMaterialOutboundService {

    private final RawMaterialOutboundRepository repository;

    // 원자재 입고 목록을 가져오는 메서드
    public List<RawMaterialOutbound> getAllOutbounds() {
        List<RawMaterialOutbound> outbounds = repository.findAll();
        System.out.println("조회데이터:" + outbounds.size());
        outbounds.forEach(inbound -> System.out.println(inbound.toString()));
        // 데이터베이스에서 원자재 입고 데이터 모두 가져오기
        return outbounds;
    }
}
