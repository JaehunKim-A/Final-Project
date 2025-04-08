package com.team1.team1project.service.finishedProduct;

import com.team1.team1project.domain.FinishedProducts;
import com.team1.team1project.dto.FinishedProductsDTO;
import com.team1.team1project.repository.FinishedProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class FinishedProductsServiceImpl implements FinishedProductsService {
    private final ModelMapper modelMapper;
    private final FinishedProductsRepository finishedProductsRepository;

    @Override
    public Long registers(FinishedProductsDTO finishedProductsDTO){
        String[] productCodes = finishedProductsDTO.getProductCode().split(",");
        String[] descriptions = finishedProductsDTO.getDescription().split(",");
        String[] productNames = finishedProductsDTO.getProductName().split(",");
        String[] units = finishedProductsDTO.getUnit().split(",");
        String[] statuses = finishedProductsDTO.getStatus().split(",");
        String[] categories = finishedProductsDTO.getCategory().split(",");
        for(int i = 0; i < productCodes.length; i++ ){
            String productCode = productCodes[i].trim();
            String description = descriptions[i].trim();
            String productName = productNames[i].trim();
            String unit = units[i].trim();
            String status = statuses[i].trim();
            String category = categories[i].trim();

            FinishedProducts finishedProducts = FinishedProducts.builder()
                    .productCode(productCode)
                    .productName(productName)
                    .description(description)
                    .unit(unit)
                    .status(status)
                    .category(category)
                    .build();
            FinishedProducts saveProducts = finishedProductsRepository.save(finishedProducts);
        }
        return 1L;
    }

    @Override
    public FinishedProductsDTO readOne(Long productId){
        Optional<FinishedProducts> readProduct = finishedProductsRepository.findById(productId);
        FinishedProducts finishedProducts = readProduct.orElseThrow(() -> new RuntimeException("ProId Not Found"));
        FinishedProductsDTO finishedProductsDTO = modelMapper.map(finishedProducts, FinishedProductsDTO.class);

        return finishedProductsDTO;
    }

    @Override
    public void modifyOne(FinishedProductsDTO finishedProductsDTO){
        Optional<FinishedProducts> readProduct = finishedProductsRepository.findById(finishedProductsDTO.getProductId());
        FinishedProducts finishedProducts = readProduct.orElseThrow(() -> new RuntimeException("ProId not Found"));

        finishedProducts.finishedProductsChange(
                finishedProductsDTO.getProductCode(),
                finishedProductsDTO.getDescription(),
                finishedProductsDTO.getCategory(),
                finishedProductsDTO.getProductName(),
                finishedProductsDTO.getStatus(),
                finishedProductsDTO.getUnit());

        finishedProductsRepository.save(finishedProducts);
    }
    @Override
    public void removeOne(Long productId){finishedProductsRepository.deleteById(productId);}

    @Override
    public List<FinishedProducts> getAllProducts(){
        return finishedProductsRepository.findAll();
    }
}
