package com.project.rest.controller;

import java.net.URI;
import jakarta.validation.Valid;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

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
import com.project.model.Projekt;
import com.project.service.ProjektService;


@RestController
@RequestMapping("/api")
public class ProjektRestController {

    private final ProjektService projektService;

    @Autowired
    public ProjektRestController(ProjektService projektService) {
        this.projektService = projektService;
    }

    @GetMapping("/projekty/{projektId}")
    public ResponseEntity<Projekt> getProjekt(@PathVariable("projektId") Integer projektId) {
        return ResponseEntity.of(projektService.getProjekt(projektId));
    }

    @PostMapping(path = "/projekty")
    public ResponseEntity<Void> createProjekt(@Valid @RequestBody Projekt projekt) {
        Projekt createdProjekt = projektService.setProjekt(projekt);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{projektId}")
                .buildAndExpand(createdProjekt.getProjektId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /*
    @PutMapping("/projekty/{projektId}")
    public ResponseEntity<Void> updateProjekt(@Valid @RequestBody Projekt projekt, @PathVariable Integer projektId) {
        return projektService.getProjekt(projektId)
                .map(p -> {
                    projektService.setProjekt(projekt);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }*/
    @PutMapping("/projekty/{projektId}")
    public ResponseEntity<Void> updateProjekt(@Valid @RequestBody Projekt updatedProjekt, @PathVariable("projektId") Integer projektId) {
        // Retrieve the existing Projekt from the database
        java.util.Optional<Projekt> existingProjektOptional = projektService.getProjekt(projektId);
        
        // Check if the Projekt exists
        if (existingProjektOptional.isPresent()) {
            Projekt existingProjekt = existingProjektOptional.get();
            
            // Update fields of the existing Projekt with the data from the updatedProjekt object
            existingProjekt.setNazwa(updatedProjekt.getNazwa());
            existingProjekt.setOpis(updatedProjekt.getOpis());// Example: Update the name
            
            // Save the updated Projekt back to the database
            projektService.setProjekt(existingProjekt);
            
            // Return ResponseEntity with status OK (200)
            return ResponseEntity.ok().build();
        } else {
            // If the Projekt with the given ID does not exist, return ResponseEntity with status NOT_FOUND (404)
            return ResponseEntity.notFound().build();
        }
    }



    

    @DeleteMapping("/projekty/{projektId}")
    public ResponseEntity<Void> deleteProjekt(@PathVariable("projektId") Integer projektId) {
        return projektService.getProjekt(projektId)
                .map(p -> {
                    projektService.deleteProjekt(projektId);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/projekty")
    public Page<Projekt> getProjekty(Pageable pageable) {
        return projektService.getProjekty(pageable);
    }

    @GetMapping(value = "/projekty", params = "nazwa")
    public Page<Projekt> getProjektyByNazwa(@RequestParam(name="nazwa")  String nazwa, Pageable pageable) {
        return projektService.searchByNazwa(nazwa, pageable);
    }
}
