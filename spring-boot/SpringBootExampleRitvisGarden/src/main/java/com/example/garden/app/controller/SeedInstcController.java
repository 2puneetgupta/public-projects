package com.example.garden.app.controller;

import com.example.garden.app.entity.Seed;
import com.example.garden.app.entity.SeedInstc;
import com.example.garden.app.service.SeedInstcService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SeedInstcController {

    private SeedInstcService seedInstcService;

    public SeedInstcController(SeedInstcService seedInstcService) {
        super();
        this.seedInstcService = seedInstcService;
    }

    // handler method to handle list seedInstc and return mode and view
    @GetMapping("/seedInstc")
    public String listSeedInstc(Model model) {
        model.addAttribute("seedInstc", seedInstcService.getAllSeedInstcs());
        return "seedInstc";
    }

    @GetMapping("/seedInstc/new")
    public String createSeedForm(Model model) {

        // create seed object to hold seed form data
        SeedInstc seedInstc = new SeedInstc();
        model.addAttribute("seedInstc", seedInstc);
        return "create_seed_instc";

    }

    @PostMapping("/seedInstc")
    public String saveSeed(@ModelAttribute("seedInstc") SeedInstc seedInstc) {
        seedInstcService.saveSeedInstc(seedInstc);
        return "redirect:/seedInstc";
    }

    @GetMapping("/seedInstc/edit/{seedName}")
    public String editSeedForm(@PathVariable String seedName, Model model) {
        model.addAttribute("seedInstc", seedInstcService.getSeedInstcById(seedName));
        return "edit_seed_instc";
    }

    @PostMapping("/seedInstc/{seedName}")
    public String updateSeed(@PathVariable String seedName,
                                @ModelAttribute("seedInstc") SeedInstc seedInstc,
                                Model model) {

        // get seed from database by seedName
        SeedInstc existingSeedInstc = seedInstcService.getSeedInstcById(seedName);
        existingSeedInstc.setSeedName(seedName);
        existingSeedInstc.setPic(seedInstc.getPic());
        existingSeedInstc.setDate(seedInstc.getDate());

        // save updated seed object
        seedInstcService.updateSeedInstc(existingSeedInstc);
        return "redirect:/seedInstc";
    }

    // handler method to handle delete seed request

    @GetMapping("/seedInstc/{seedName}")
    public String deleteSeed(@PathVariable String seedName) {
        seedInstcService.deleteSeedInstcById(seedName);
        return "redirect:/seedInstc";
    }
}
