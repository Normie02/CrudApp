package org.example.crudapp.service;

import org.example.crudapp.model.Student;
import org.example.crudapp.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void createStudent(Student student) {
        studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        Optional<Student> studentRes = studentRepository.findById(id);
        return studentRes.orElse(null);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student updateStudent(Long id, Student studentReq) {

        Optional<Student> studentToUpdate = studentRepository.findById(id);

        if (studentToUpdate.isEmpty()) {
            return null;
        }
        Student studentToSave = studentToUpdate.get();

        studentToSave.setName(studentReq.getName());
        studentToSave.setAge(studentReq.getAge());
        studentToSave.setEmail(studentReq.getEmail());
        studentToSave.setRollNo(studentReq.getRollNo());
        studentToSave.setSubject(studentReq.getSubject());

        studentRepository.save(studentToSave);

        return studentToSave;

    }

    public Boolean deleteStudent(Long id){

            Boolean studentExist = studentRepository.existsById(id);

            if(!studentExist) return false;

            studentRepository.deleteById(id);

            return true;

    }

}
