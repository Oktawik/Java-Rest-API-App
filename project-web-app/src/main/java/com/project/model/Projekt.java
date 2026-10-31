package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Projekt {
	
	private Integer projektId;
	
	@NotBlank(message = "Pole nazwa nie moze byc puste")
    @Size(min = 3, max = 50, message = "Nazwa musi zawierac od {min} do {max} znakow")
	private String nazwa;
	
	@Size(min = 3, max = 1000, message = "Opis musi zawierac od {min} do {max} znakow")
	private String opis;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private LocalDateTime dataCzasUtworzenia;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime dataCzasModyfikacji;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataOddania;

	@JsonIgnoreProperties({"projekt"})
    private List<Zadanie> zadania;
    
    private Set<Student> studenci;
}
