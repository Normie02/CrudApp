package org.example.crudapp.controller;

import org.example.crudapp.model.Student;
import org.example.crudapp.service.StudentService;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    // create - POST - /api/students
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
      Student createdStudent =  studentService.createStudent(student);
      return ResponseEntity. status(HttpStatus.CREATED).body(createdStudent);
//        status(HttpStatus.CREATED).body(createdStudent)
    }
    // read one record - GET - /api/students/1
    @GetMapping("/get/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        Student studentRes = studentService.getStudent(id);
        if(studentRes == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentRes);
    }
    // read all - GET - /api/students
    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAllStudent(){
        List <Student> studentList = studentService.getAllStudent();
        if(studentList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentList);
    }
    // update - PUT - /api/students/1
    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,@RequestBody Student studentReq){
        Student studentRes = studentService.updateStudent(id,studentReq);
        if(studentRes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentRes );
    }
    //delete - DELETE - /api/students/1
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        Boolean isDeleted = studentService.deleteStudent(id);

        if(!isDeleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Record deleted");
    }
}
