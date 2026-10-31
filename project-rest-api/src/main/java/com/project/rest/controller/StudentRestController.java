package com.project.rest.controller;

import java.net.URI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.project.model.Student;
import com.project.service.ProjektService;
import com.project.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private final StudentService studentService;
    private ProjektService projektService;
    
    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/studenci/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Integer studentId) {
        return ResponseEntity.of(studentService.getStudent(studentId));
    }

    @PostMapping("/studenci")
    public ResponseEntity<Void> createStudent(@Valid @RequestBody Student student) {
        Student createdStudent = studentService.setStudent(student);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{studentId}")
                .buildAndExpand(createdStudent.getStudentId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/studenci/{studentId}")
    public ResponseEntity<Void> updateStudent(@Valid @RequestBody Student student, @PathVariable Integer studentId) {
        return studentService.getStudent(studentId)
                .map(s -> {
                    student.setStudentId(studentId);
                    studentService.setStudent(student);
                    return ResponseEntity.ok().<Void>build(); // Rzutowanie do ResponseEntity<Void>
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/studenci/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer studentId) {
        return studentService.getStudent(studentId)
                .map(s -> {
                    studentService.deleteStudent(studentId);
                    return ResponseEntity.ok().<Void>build(); // Rzutowanie do ResponseEntity<Void>
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/studenci")
    public Page<Student> getStudents(Pageable pageable) {
        return studentService.getStudenci(pageable);
    }
}
