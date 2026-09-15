package org.example.crudapp.controller;

import org.example.crudapp.dto.CreateStudentRequestDto;
import org.example.crudapp.dto.CreateStudentResponseDto;
import org.example.crudapp.dto.UpdateStudentRequestDto;
import org.example.crudapp.dto.UpdateStudentResponseDto;
import org.example.crudapp.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {


    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping//
    public ResponseEntity<CreateStudentResponseDto> createStudent(@Valid @RequestBody CreateStudentRequestDto studentRequestDTO) {
        CreateStudentResponseDto studentCreated = studentService.createStudent(studentRequestDTO);
        if (studentCreated.equals("Student already exist")) {
            return ResponseEntity.status(406).body(studentCreated);
        } else if (studentCreated.equals("Cannot")) {
            return ResponseEntity.status(406).body(studentCreated);
        }
        return ResponseEntity
                .status(201)
                .body(studentCreated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreateStudentResponseDto> getStudent(@PathVariable Long id) {
        CreateStudentResponseDto studentResp = studentService.getStudent(id);

        return ResponseEntity
                .ok(studentResp);
    }

    @GetMapping
    public ResponseEntity<List<CreateStudentResponseDto>> getAllStudents() {
        List<CreateStudentResponseDto> studentList = studentService.getAllStudents();

        return ResponseEntity
                .ok(studentList);
    }

    @PutMapping
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@RequestParam Long id, @RequestBody UpdateStudentRequestDto studentReq) {
        UpdateStudentResponseDto studentResp = studentService.updateStudent(id, studentReq);

        return ResponseEntity
                .ok(studentResp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
         studentService.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllStudent() {

        boolean isDeleted = studentService.deleteAllStudent();

        if (!isDeleted) {
            return ResponseEntity.status(404).body("Already empty database");
        }
        return ResponseEntity.ok("All records deleted");

    }

    @PatchMapping("/soft-delete")
    public ResponseEntity<String> softDelete(@RequestParam Long id) {
         studentService.deleteStudentSoftly(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
