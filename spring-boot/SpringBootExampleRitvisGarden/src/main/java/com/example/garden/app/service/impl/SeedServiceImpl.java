package com.example.garden.app.service.impl;

import com.example.garden.app.entity.Seed;
import com.example.garden.app.repository.SeedRepository;
import com.example.garden.app.service.SeedService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeedServiceImpl implements SeedService {

    private SeedRepository seedRepository;

    public SeedServiceImpl(SeedRepository seedRepository) {
        super();
        this.seedRepository = seedRepository;
    }

    @Override
    public List<Seed> getAllSeeds() {
        return seedRepository.findAll();
    }

    @Override
    public Seed saveSeed(Seed seed) {
        return seedRepository.save(seed);
    }

    @Override
    public Seed getSeedById(String seedName) {
        return seedRepository.findById(seedName).get();
    }

    @Override
    public Seed updateSeed(Seed seed) {
        return seedRepository.save(seed);
    }

    @Override
    public void deleteSeedById(String seedName) {
        seedRepository.deleteById(seedName);
    }
}
