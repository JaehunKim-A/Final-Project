package com.team1.team1project.service.finishedProduct;

import com.team1.team1project.domain.FinishedProductStock;
import com.team1.team1project.repository.FinishedProductStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class FinishedProductStockServiceImpl implements FinishedProductStockService{
    private final ModelMapper modelMapper;
    private final FinishedProductStockRepository finishedProductStockRepository;
    @Override
    public List<FinishedProductStock> getAllStock(){
        return finishedProductStockRepository.findAll();
    }
}
