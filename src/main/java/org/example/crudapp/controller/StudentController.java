package org.example.crudapp.controller;

import org.example.crudapp.model.Student;
import org.example.crudapp.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {


    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping//
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
      studentService.createStudent(student);
        return ResponseEntity
                .status(201)
                .body("Student Created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student studentResp = studentService.getStudent(id);

        if (studentResp == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity
                .status(200)
                .body(studentResp);
    }
    @GetMapping()
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> studentList = studentService.getAllStudents();

        if (studentList.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity
                .ok(studentList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentReq) {
        Student studentResp = studentService.updateStudent(id, studentReq);

        if (studentResp == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity
                .status(200)
                .body(studentResp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {

        boolean isDeleted = studentService.deleteStudent(id);

        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Record deleted");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllStudent() {

        boolean isDeleted = studentService.deleteAllStudent();

        if(!isDeleted) {
            return ResponseEntity.status(404).body("Already empty database");
        }
        return ResponseEntity.ok("All records deleted");

    }
//    @PatchMapping("/id")
//    public ResponseEntity<String> softDelete(@PathVariable Long id) {
//        Boolean isDeleted = studentService.deleteStudentSoftly(id);
//
//        if(!isDeleted) return ResponseEntity.notFound().build();
//
//        return ResponseEntity.ok("Record deleted ");
//
//
//    }

}
