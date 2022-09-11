package com.example.garden.app.controller;

import com.example.garden.app.entity.Seed;
import com.example.garden.app.service.SeedService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SeedController {

    private SeedService seedService;

    public SeedController(SeedService seedService) {
        super();
        this.seedService = seedService;
    }

    // handler method to handle list seeds and return mode and view
    @GetMapping("/seeds")
    public String listSeeds(Model model) {
        model.addAttribute("seeds", seedService.getAllSeeds());
        return "seeds";
    }

    @GetMapping("/seeds/new")
    public String createSeedForm(Model model) {

        // create seed object to hold seed form data
        Seed seed = new Seed();
        model.addAttribute("seed", seed);
        return "create_seed";

    }

    @PostMapping("/seeds")
    public String saveSeed(@ModelAttribute("seed") Seed seed) {
        seedService.saveSeed(seed);
        return "redirect:/seeds";
    }

    @GetMapping("/seeds/edit/{seedName}")
    public String editSeedForm(@PathVariable String seedName, Model model) {
        model.addAttribute("seed", seedService.getSeedById(seedName));
        return "edit_seed";
    }

    @PostMapping("/seeds/{seedName}")
    public String updateSeed(@PathVariable String seedName,
                                @ModelAttribute("seed") Seed seed,
                                Model model) {

        // get seed from database by seedName
        Seed existingSeed = seedService.getSeedById(seedName);
        existingSeed.setSeedName(seedName);
        existingSeed.setCompany(seed.getCompany());

        // save updated seed object
        seedService.updateSeed(existingSeed);
        return "redirect:/seeds";
    }

    // handler method to handle delete seed request

    @GetMapping("/seeds/{seedName}")
    public String deleteSeed(@PathVariable String seedName) {
        seedService.deleteSeedById(seedName);
        return "redirect:/seeds";
    }
}
