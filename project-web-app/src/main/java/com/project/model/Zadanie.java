package com.project.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Zadanie {
    private Integer zadanieId;

    @NotBlank(message = "Pole nie moze byc puste")
    @Size(min = 3, max = 50, message = "Nazwa musi zawierac od {min} do {max} znakow!")
    private String nazwa;

    private Integer kolejnosc;

    @Size(max = 1000, message = "Opis moze zawierac maksymalnie {max} znakow")
    private String opis;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime dataCzasDodania;
    
    // mozliwe ze to trzeba skasowac
    private Projekt projekt;
}
