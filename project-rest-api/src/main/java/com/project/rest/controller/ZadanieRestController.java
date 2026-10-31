package com.project.rest.controller;

import java.net.URI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.project.model.Zadanie;
import com.project.service.ZadanieService;

@RestController
@RequestMapping("/api")
public class ZadanieRestController {

    private final ZadanieService zadanieService;

    @Autowired
    public ZadanieRestController(ZadanieService zadanieService) {
        this.zadanieService = zadanieService;
    }

    @GetMapping("/zadania/{zadanieId}")
    public ResponseEntity<Zadanie> getZadanie(@PathVariable Integer zadanieId) {
        return ResponseEntity.of(zadanieService.getZadanie(zadanieId));
    }

    @PostMapping("/zadania")
    public ResponseEntity<Void> createZadanie(@Valid @RequestBody Zadanie zadanie) {
        Zadanie createdZadanie = zadanieService.setZadanie(zadanie);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{zadanieId}")
                .buildAndExpand(createdZadanie.getZadanieId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/zadania/{zadanieId}")
    public ResponseEntity<Void> updateZadanie(@Valid @RequestBody Zadanie zadanie, @PathVariable Integer zadanieId) {
        return zadanieService.getZadanie(zadanieId)
                .map(z -> {
                    zadanieService.setZadanie(zadanie);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/zadania/{zadanieId}")
    public ResponseEntity<Void> deleteZadanie(@PathVariable Integer zadanieId) {
        return zadanieService.getZadanie(zadanieId)
                .map(z -> {
                    zadanieService.deleteZadanie(zadanieId);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/zadania")
    public Page<Zadanie> getZadania(Pageable pageable) {
        return zadanieService.getZadania(pageable);
    }

    @GetMapping(value = "/zadania", params = "projektId")
    public Page<Zadanie> getZadaniaByProjekt(@RequestParam Integer projektId, Pageable pageable) {
        return zadanieService.getZadaniaProjektu(projektId, pageable);
    }
}
