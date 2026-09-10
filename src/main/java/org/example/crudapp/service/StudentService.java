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

    public String createStudent(Student student) {
        Optional<Student> isExistAndIsDeletedFalse = studentRepository.findByIdAndDeletedIsFalse(student.getId());
        if(isExistAndIsDeletedFalse.isPresent()) {
            return "Student already exist with this id : " + student.getId();
        }

        Optional<Student> isExistAndIsDeletedTrue = studentRepository.findByIdAndDeletedIsTrue(student.getId());
        if(isExistAndIsDeletedTrue.isPresent()) {
            return "Cannot create student with this id : " + student.getId() ;
        }
        student.setDeleted(false);
        studentRepository.save(student);
        return "Student created";
    }

    public Student getStudent(Long id) {
        Optional<Student> studentRes = studentRepository.findByIdAndDeletedIsFalse(id);

        return studentRes.orElse(null);
    }

    public List<Student> getAllStudents() {

        List<Student> studentList = studentRepository.findByDeletedIsFalse();

//        return studentRepository.findAll();
        return studentList;
    }

    public Student updateStudent(Long id, Student studentReq) {

        Optional<Student> studentToUpdate = studentRepository.findByIdAndDeletedIsFalse(id);

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
    public Boolean deleteAllStudent() {

        Long totalRecords = studentRepository.count();

        if(totalRecords == 0) {
            return false;
        } else {
            studentRepository.deleteAll();
        }
        return true;

    }

    public Boolean deleteStudentSoftly(Long id) {

        Optional<Student> isPresent = studentRepository.findByIdAndDeletedIsFalse(id);

        if(isPresent.isPresent()) {
            Student student = isPresent.get();
            if(student.getDeleted()) {
                return true;
            }
            student.setDeleted(true);
            studentRepository.save(student);
            return true;
        }
        return false;




    }



}
