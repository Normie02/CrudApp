package org.example.crudapp.service;

import org.example.crudapp.dto.CreateStudentRequestDto;
import org.example.crudapp.dto.CreateStudentResponseDto;
import org.example.crudapp.dto.UpdateStudentRequestDto;
import org.example.crudapp.dto.UpdateStudentResponseDto;
import org.example.crudapp.exception.DuplicateResourceException;
import org.example.crudapp.exception.ResourceNotFoundException;
import org.example.crudapp.model.Student;
import org.example.crudapp.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentRequestDTO) {
        Student student = mapToEntity(studentRequestDTO);

        if (emailExist(student)) {
            throw new DuplicateResourceException("Student with email " + student.getEmail() + " already exist");
        }

        Student studentResp = studentRepository.save(student);

        return mapToDto(studentResp);

    }

    public CreateStudentResponseDto getStudent(Long id) {
        Student studentRes = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));


        return mapToDto(studentRes);

    }

    public List<CreateStudentResponseDto> getAllStudents() {

        List<Student> studentList = studentRepository.findByDeletedIsFalse();


//        return studentRepository.findAll();
        return studentList.stream().map(this::mapToDto).toList();
    }

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentReq) {

        Student studentToUpdate = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));

        studentToUpdate.setName(studentReq.getName());
        studentToUpdate.setAge(studentReq.getAge());
        studentToUpdate.setRollNo(studentReq.getRollNo());
        studentToUpdate.setSubject(studentReq.getSubject());
        studentToUpdate.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(studentToUpdate);

        return mapToUpdateDto(studentToUpdate);
    }

    public void deleteStudent(Long id) {

        Student studentToBeDeleted = studentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id : " + id + " not found"));

        studentRepository.delete(studentToBeDeleted);
    }

    public Boolean deleteAllStudent() {

        Long totalRecords = studentRepository.count();

        if (totalRecords == 0) {
            return false;
        } else {
            studentRepository.deleteAll();
        }
        return true;

    }

    public void deleteStudentSoftly(Long id) {

        Student studentToBeDeleted = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id : " + id + " not found"));


        studentToBeDeleted.setUpdatedAt(LocalDateTime.now());
        studentToBeDeleted.setDeleted(true);
    }

    private Student mapToEntity(CreateStudentRequestDto studentRequestDto) {
        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setAge(studentRequestDto.getAge());
        student.setEmail(studentRequestDto.getEmail());
        student.setRollNo(studentRequestDto.getRollNo());
        student.setSubject(studentRequestDto.getSubject());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        student.setDeleted(false);
        return student;
    }


    private CreateStudentResponseDto mapToDto(Student student) {
        CreateStudentResponseDto responseDto = new CreateStudentResponseDto();

        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setSubject(student.getSubject());
        responseDto.setMessage("Student saved successfully");
        responseDto.setCreatedAt(student.getCreatedAt());
        responseDto.setUpdatedAt(student.getUpdatedAt());

        return responseDto;

    }

    private UpdateStudentResponseDto mapToUpdateDto(Student student) {
        UpdateStudentResponseDto responseDto = new UpdateStudentResponseDto();

        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setSubject(student.getSubject());
        responseDto.setMessage("Student updated successfully");
        responseDto.setUpdatedAt(student.getUpdatedAt());

        return responseDto;

    }

    private Boolean emailExist(Student student) {
        return studentRepository.existsByEmail(student.getEmail());
    }

}
