package com.team1.team1project.service.finishedProduct;

import com.team1.team1project.domain.FinishedProductCombos;
import java.util.List;
import java.util.Optional;

public interface FinishedProductCombosService {
    List<FinishedProductCombos> findAll();
    Optional<FinishedProductCombos> findById(Long id);
    FinishedProductCombos save(FinishedProductCombos combo);
    void delete(Long id);
}
