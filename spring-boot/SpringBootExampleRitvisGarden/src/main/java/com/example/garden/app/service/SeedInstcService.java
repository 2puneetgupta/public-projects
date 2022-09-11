package com.example.garden.app.service;

import com.example.garden.app.entity.SeedInstc;

import java.util.List;

public interface SeedInstcService {
    List<SeedInstc> getAllSeedInstcs();

    SeedInstc saveSeedInstc(SeedInstc seedInstc);

    SeedInstc getSeedInstcById(String seedInstcName);

    SeedInstc updateSeedInstc(SeedInstc seedInstc);

    void deleteSeedInstcById(String seedInstcName);
}
