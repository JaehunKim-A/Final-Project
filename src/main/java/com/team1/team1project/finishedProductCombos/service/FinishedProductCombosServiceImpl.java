package com.team1.team1project.finishedProductCombos.service;

import com.team1.team1project.domain.FinishedProductCombos;
import com.team1.team1project.finishedProductCombos.repository.FinishedProductCombosRepository;
import com.team1.team1project.finishedProductCombos.service.FinishedProductCombosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinishedProductCombosServiceImpl implements FinishedProductCombosService {

    private final FinishedProductCombosRepository repository;

    @Autowired
    public FinishedProductCombosServiceImpl(FinishedProductCombosRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FinishedProductCombos> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<FinishedProductCombos> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public FinishedProductCombos save(FinishedProductCombos combo) {
        return repository.save(combo);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}