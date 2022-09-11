package com.example.garden.app.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "seed_instc")
public class SeedInstc {
    @Id
    @Column(name="seed_name")
    private String seedName;
    @Column(name="date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @Column(name="pic")
    private String pic;

    public SeedInstc() {
    }

    public SeedInstc(String seedName, String pic,Date date) {
        this.seedName = seedName;
        this.pic = pic;
        this.date = date;
    }

    public String getSeedName() {
        return seedName;
    }

    public void setSeedName(String seedName) {
        this.seedName = seedName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
