package com.example.garden.app.repository;

import com.example.garden.app.entity.Seed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeedRepository extends JpaRepository<Seed, String>{

}
