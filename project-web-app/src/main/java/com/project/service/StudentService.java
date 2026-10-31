package com.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.model.Projekt;
import com.project.model.Student;

public interface StudentService {
	Optional<Student> getStudent(Integer studentId);
	Optional<Student> getStudentByNrIndeksu(String nrIndeksu);
	Student setStudent(Student student);
	void deleteStudent(Integer studentId);
	Page<Student> getStudenci(Pageable pageable);
	Page<Student> searchByNrIndeksu(String nrIndeksu, Pageable pageable);
	Page<Student> searchByNazwisko(String nazwisko, Pageable pageable);	
	List<Projekt> getProjekty(Integer studentId);
}
