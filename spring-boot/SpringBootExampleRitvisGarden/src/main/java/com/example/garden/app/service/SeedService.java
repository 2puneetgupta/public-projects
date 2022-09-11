package com.example.garden.app.service;

import com.example.garden.app.entity.Seed;

import java.util.List;

public interface  SeedService {
    List<Seed> getAllSeeds();

    Seed saveSeed(Seed seed);

    Seed getSeedById(String seedName);

    Seed updateSeed(Seed seed);

    void deleteSeedById(String seedName);
}
