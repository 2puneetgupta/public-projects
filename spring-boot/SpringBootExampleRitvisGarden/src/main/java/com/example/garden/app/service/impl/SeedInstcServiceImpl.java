package com.example.garden.app.service.impl;

import com.example.garden.app.entity.Seed;
import com.example.garden.app.entity.SeedInstc;
import com.example.garden.app.repository.SeedInstcRepository;
import com.example.garden.app.service.SeedInstcService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeedInstcServiceImpl implements SeedInstcService {

    private SeedInstcRepository seedInstcRepository;

    public SeedInstcServiceImpl(SeedInstcRepository seedInstcRepository) {
        super();
        this.seedInstcRepository = seedInstcRepository;
    }

    @Override
    public List<SeedInstc> getAllSeedInstcs() {
        return seedInstcRepository.findAll();
    }

    @Override
    public SeedInstc saveSeedInstc(SeedInstc seedInstc) {
        return seedInstcRepository.save(seedInstc);
    }

    @Override
    public SeedInstc getSeedInstcById(String seedName) {
        return seedInstcRepository.findById(seedName).get();
    }

    @Override
    public SeedInstc updateSeedInstc(SeedInstc seedInstc) {
        return seedInstcRepository.save(seedInstc);
    }

    @Override
    public void deleteSeedInstcById(String seedName) {
        seedInstcRepository.deleteById(seedName);
    }
}
