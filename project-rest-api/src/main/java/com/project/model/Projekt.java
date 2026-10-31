package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projekt", indexes = { @Index(name = "idx_nazwaProjekt", columnList = "nazwa", unique = false) })
public class Projekt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "projekt_id")
    private Integer projektId;

    @NotBlank(message = "Pole nazwa nie moze byc puste")
    @Size(min = 3, max = 50, message = "Nazwa musi zawierac od {min} do {max} znakow")
    @Column(nullable = false, length = 50)
    private String nazwa;

    @Size(min = 3, max = 1000, message = "Opis musi zawierac od {min} do {max} znakow")
    @Column(length = 1000, nullable = true)
    private String opis;

    @CreationTimestamp
    @Column(name = "dataczas_utworzenia", nullable = true, updatable = false)
    private LocalDateTime dataCzasUtworzenia;
    
    @UpdateTimestamp
    @Column(name = "dataczas_modyfikacji", nullable = false)
    private LocalDateTime dataCzasModyfikacji;

    //@NotNull(message = "Pole data_oddania nie moze byc puste")
    //@UpdateTimestamp
    @Column(name = "data_oddania", nullable = false)
    private LocalDate dataOddania;

    @OneToMany(mappedBy = "projekt", /*cascade = CascadeType.ALL,*/ orphanRemoval = true)
    @JsonIgnoreProperties({"projekt"})
    private List<Zadanie> zadania;

    @ManyToMany
    @JoinTable(name = "projekt_student", 
               joinColumns = {@JoinColumn(name = "projekt_id")}, 
               inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private Set<Student> studenci;

    // Pusty konstruktor
    public Projekt() {
    	//this.zadania = new ArrayList<>();
    }

    // Konstruktor z nazwą i opisem
    public Projekt(String nazwa, String opis) {
        this.nazwa = nazwa;
        this.opis = opis;
        //this.zadania = new ArrayList<>();
    }
    
    public Projekt(String nazwa, String opis, LocalDateTime dataCzasUtworzenia,LocalDate dataOddania) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataCzasUtworzenia = dataCzasUtworzenia;
        this.dataOddania = dataOddania;
        //this.zadania = new ArrayList<>();
    }
    
    
    public Projekt(String nazwa, String opis, LocalDateTime dataCzasUtworzenia,LocalDateTime dataCzasModyfikacji, LocalDate dataOddania) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataCzasUtworzenia = dataCzasUtworzenia;
        this.dataCzasModyfikacji = dataCzasModyfikacji;
        this.dataOddania = dataOddania;
        // dodaje tutaj inicjalizacje pustych zadan, zeby bledow nie bylo
        //this.zadania = new ArrayList<>();
    }

	public Integer getProjektId() {
		return projektId;
	}

	public void setProjektId(Integer projektId) {
		this.projektId = projektId;
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public LocalDate getDataOddania() {
		return dataOddania;
	}

	public void setDataCzasUtworzenia(LocalDateTime dataCzasUtworzenia) {
		this.dataCzasUtworzenia = dataCzasUtworzenia;
	}
	
	public LocalDateTime getDataCzasUtworzenia() {
		return dataCzasUtworzenia;
	}
	
	public void setDataCzasModyfikacji(LocalDateTime dataCzasModyfikacji) {
		this.dataCzasModyfikacji = dataCzasModyfikacji;
	}
	
	public LocalDateTime getDataCzasModyfikacji() {
		return dataCzasModyfikacji;
	}

	public void setDataOddania(LocalDate dataOddania) {
		this.dataOddania = dataOddania;
	}

	public List<Zadanie> getZadania() {
		return zadania;
	}

	public void setZadania(List<Zadanie> zadania) {
		this.zadania = zadania;
	}

	public Set<Student> getStudenci() {
		return studenci;
	}

	public void setStudenci(Set<Student> studenci) {
		this.studenci = studenci;
	}
}
