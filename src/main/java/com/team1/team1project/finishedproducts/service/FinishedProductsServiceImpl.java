package com.team1.team1project.finishedproducts.service;

import com.team1.team1project.domain.FinishedProducts;
import com.team1.team1project.dto.FinishedProductsDTO;
import com.team1.team1project.finishedproducts.repository.FinishedProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@Service
@Log4j2
@RequiredArgsConstructor
public class FinishedProductsServiceImpl implements FinishedProductsService {
    private final ModelMapper modelMapper;
    private final FinishedProductsRepository finishedProductsRepository;

    @Override
    public Integer[] registers(FinishedProductsDTO[] finishedProductsDTOs){
        Integer[] productIds = new Integer[finishedProductsDTOs.length];
        for(int i = 0; i < finishedProductsDTOs.length; i++){
            FinishedProducts finishedProducts = modelMapper.map(finishedProductsDTOs[i], FinishedProducts.class);
            productIds[i] = finishedProductsRepository.save(finishedProducts).getProductId();
        }

        return productIds;
    }

    @Override
    public FinishedProductsDTO readOne(Integer productId){
        Optional<FinishedProducts> result = finishedProductsRepository.findById(productId);
        FinishedProducts finishedProducts = result.orElseThrow(() ->
                new EntityNotFoundException("Product not Found with Id" + productId));
        return modelMapper.map(finishedProducts, FinishedProductsDTO.class);
    }

    @Override
    public void modifyOne(FinishedProductsDTO finishedProductsDTO){
        Optional<FinishedProducts> result = finishedProductsRepository.findById(finishedProductsDTO.getProductId());
        if(result.isPresent()){
            FinishedProducts finishedProduct = result.get();
            finishedProduct.finishedProductsChange(finishedProductsDTO.getCodeId(), finishedProductsDTO.getProductDescription(), finishedProductsDTO.getStatus());
            finishedProductsRepository.save(finishedProduct);
        } else {
            throw new EntityNotFoundException("Product not Found with Id " + finishedProductsDTO.getProductId());
        }
    }

    @Override
    public void removeOne(Integer productId){
        finishedProductsRepository.deleteById(productId);
    }
}
