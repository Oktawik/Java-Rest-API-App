package com.project.controller;

import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.model.Zadanie;
import com.project.service.ZadanieService;
import com.project.service.ProjektService;
import com.project.model.Projekt; // Dodajemy import klasy Projekt

@Controller
public class ZadanieController {
    private final ZadanieService zadanieService;
    private final ProjektService projektService;

    public ZadanieController(ZadanieService zadanieService, ProjektService projektService) {
        this.zadanieService = zadanieService;
        this.projektService = projektService;
    }

    @GetMapping("/zadanieList")
    public String zadanieList(Model model, Pageable pageable) {
        model.addAttribute("zadania", zadanieService.getZadania(pageable).getContent());
        return "zadanieList";
    }

    @GetMapping("/zadanieEdit")
    public String zadanieEdit(@RequestParam(required = false) Integer zadanieId, Model model) {
        Zadanie zadanie = zadanieId != null ? 
            zadanieService.getZadanie(zadanieId).orElse(new Zadanie()) :
            new Zadanie();
        model.addAttribute("zadanie", zadanie);
        // Pobieramy listę wszystkich projektów
        Pageable pageable = PageRequest.of(0, 20); // Numer strony: 0, Rozmiar strony: 20
        Iterable<Projekt> projekty = projektService.getProjekty(pageable);
        model.addAttribute("projekty", projekty);
        // Jeśli zadanie ma przypisany projekt, ustawiamy zaznaczenie w formularzu
        if (zadanie.getProjekt() != null) {
            model.addAttribute("wybranyProjekt", zadanie.getProjekt().getProjektId());
        }
        return "zadanieEdit";
    }

    /*
    @PostMapping("/zadanieEdit")
    public String zadanieEditSave(@ModelAttribute @Valid Zadanie zadanie, BindingResult bindingResult, 
                                  @RequestParam(required = false) Integer projektId) {
        if (bindingResult.hasErrors()) {
            return "zadanieEdit";
        }
        
        // Jeśli wybrano projekt, pobieramy obiekt Projekt na podstawie identyfikatora
        if (projektId != null) {
            Projekt projekt = projektService.getProjekt(projektId).orElse(null);
            zadanie.setProjekt(projekt);
        }
        
        zadanieService.setZadanie(zadanie);
        return "redirect:/zadanieList";
    }*/
    @PostMapping("/zadanieEdit")
    public String zadanieEditSave(@ModelAttribute @Valid Zadanie zadanie, BindingResult bindingResult, 
                                  @RequestParam(required = false) Integer projektId) {
        if (bindingResult.hasErrors()) {
            return "zadanieEdit";
        }
        
        // Sprawdź, czy wybrano projekt
        if (projektId != null) {
            Projekt projekt = projektService.getProjekt(projektId).orElse(null);
            zadanie.setProjekt(projekt);
        } else {
            // Jeśli nie wybrano projektu, ustaw specjalną wartość oznaczającą brak przypisanego projektu
            Projekt brakProjektu = new Projekt();
            brakProjektu.setProjektId(-1); // Dla przykładu używamy identyfikatora -1
            zadanie.setProjekt(brakProjektu);
        }
        
        zadanieService.setZadanie(zadanie);
        return "redirect:/zadanieList";
    }


    @PostMapping(params = "cancel", path = "/zadanieEdit")
    public String zadanieEditCancel() {
        return "redirect:/zadanieList";
    }

    @PostMapping(params = "delete", path = "/zadanieEdit")
    public String zadanieEditDelete(@ModelAttribute Zadanie zadanie) {
        zadanieService.deleteZadanie(zadanie.getZadanieId());
        return "redirect:/zadanieList";
    }
}

