package com.project.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {
	private Integer studentId;
	
	@NotBlank(message = "Pole imie nie moze byc puste")
    @Size(min = 2, max = 50, message = "Imie moze zawierac od {min} do {max} znakow")
    private String imie;
	
	@NotBlank(message = "Pole nazwisko nie moze byc puste")
    @Size(min = 2, max = 100, message = "Nazwisko moze zawierac od {min} do {max} znakow")
    private String nazwisko;

    @NotBlank(message = "Pole nr_indeksu nie moze byc puste")
    @Size(min = 1, max = 20, message = "Nr_indeksu musi zawierac od {min} do {max} znakow")
    private String nrIndeksu;

    @NotBlank(message = "Pole email nie moze byc puste")
    @Size(min = 2, max = 50, message = "Email moze zawierac od {min} do {max} znakow")
    private String email;

    @NotNull(message = "Pole stacjonarny nie moze byc puste")
    private Boolean stacjonarny;
    
    @JsonIgnoreProperties("studenci")
    private Set<Projekt> projekty;
}
