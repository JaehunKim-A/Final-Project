package com.team1.team1project.service.rawMaterial;

import com.team1.team1project.domain.RawMaterialStock;
import com.team1.team1project.repository.RawMaterialStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class RawMaterialStockServiceImpl implements RawMaterialStockService{
    private final ModelMapper modelMapper;
    private final RawMaterialStockRepository rawMaterialStockRepository;
    @Override
    public List<RawMaterialStock> getAllStock(){
        return rawMaterialStockRepository.findAll();
    }
}
