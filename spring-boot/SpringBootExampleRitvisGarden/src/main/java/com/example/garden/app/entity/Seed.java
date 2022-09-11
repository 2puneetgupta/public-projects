package com.example.garden.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seed")
public class Seed {
    @Id
    @Column(name="seed_name")
    private String seedName;
    @Column(name="company")
    private String company;

    public Seed() {
    }

    public Seed(String seedName, String company) {
        this.seedName = seedName;
        this.company = company;
    }

    public String getSeedName() {
        return seedName;
    }

    public void setSeedName(String seedName) {
        this.seedName = seedName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
