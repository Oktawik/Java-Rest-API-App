package com.project.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "zadanie", indexes = {
        @Index(name = "idx_nazwaZadanie", columnList = "nazwa", unique = false),
        @Index(name = "idx_kolejnoscZadanie", columnList = "kolejnosc", unique = false)
})
public class Zadanie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zadanie_id")
    private Integer zadanieId;

    @NotBlank(message = "Pole nie moze byc puste")
    @Size(min = 3, max = 50, message = "Nazwa musi zawierac od {min} do {max} znakow!")
    @Column(nullable = false, length = 50)
    private String nazwa;

    @Column(nullable = true)
    private Integer kolejnosc;

    @Size(max = 1000, message = "Opis moze zawierac maksymalnie {max} znakow")
    @Column(length = 1000, nullable = true)
    private String opis;

    @CreationTimestamp
    @Column(name = "dataczas_dodania", nullable = false, updatable = false)
    private LocalDateTime dataCzasDodania;

    @ManyToOne
    //@JsonIgnoreProperties({"zadanie"})
    @JoinColumn(name = "projekt_id", nullable = true)
    private Projekt projekt;

    // Pusty konstruktor
    public Zadanie() {}
    
    
    public Zadanie(String nazwa, String opis, Integer kolejnosc) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.kolejnosc = kolejnosc;
    }

    // Konstruktor z nazwą, opisem i kolejnością
    public Zadanie(String nazwa, String opis, Integer kolejnosc, LocalDateTime dataCzasDodania) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.kolejnosc = kolejnosc;
        this.dataCzasDodania = dataCzasDodania;
    }

	public Integer getZadanieId() {
		return zadanieId;
	}

	public void setZadanieId(Integer zadanieId) {
		this.zadanieId = zadanieId;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public Integer getKolejnosc() {
		return kolejnosc;
	}

	public void setKolejnosc(Integer kolejnosc) {
		this.kolejnosc = kolejnosc;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public LocalDateTime getDataCzasDodania() {
		return dataCzasDodania;
	}

	public void setDataCzasDodania(LocalDateTime dataCzasDodania) {
		this.dataCzasDodania = dataCzasDodania;
	}

	public Projekt getProjekt() {
		return projekt;
	}

	public void setProjekt(Projekt projekt) {
		this.projekt = projekt;
	}

    // Gettery i settery...
    
}
