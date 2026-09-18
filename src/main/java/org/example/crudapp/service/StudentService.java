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

    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentRequestDTO) throws DuplicateResourceException {
        Student student = mapToEntity(studentRequestDTO);

        if (emailExist(student)) {
           throw new DuplicateResourceException("Student with email " + student.getEmail() + " already exist");
        }

        Student studentResp = studentRepository.save(student);
        return mapToDto(studentResp);

    }

    public CreateStudentResponseDto getStudent(Long id) {
       Student student=  studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));

       return mapToDto(student);
    }

    public List<CreateStudentResponseDto> getAllStudents() {

        List<Student> studentList = studentRepository.findByDeletedIsFalse();


//        return studentRepository.findAll();
        return studentList.stream()
                .map(this::mapToDto)
                .toList();
    }

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentReq) {

        Student student = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));

        student.setName(studentReq.getName());
        student.setAge(studentReq.getAge());
        student.setRollNo(studentReq.getRollNo());
        student.setSubject(studentReq.getSubject());
        student.setUpdatedAt(LocalDateTime.now());
        Student savedStudent = studentRepository.save(student);

        return mapToUpdateDto(savedStudent);
    }

    public void deleteStudent(Long id) {

       Student student = studentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id : " + id + " not found"));

        studentRepository.delete(student);
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

        Student student = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id : " + id + " not found"));

        student.setDeleted(true);
        student.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(student);
    }

    private Student mapToEntity(CreateStudentRequestDto studentRequestDto) {

        Student studentToSave = new Student();

        studentToSave.setName(studentRequestDto.getName());
        studentToSave.setAge(studentRequestDto.getAge());
        studentToSave.setEmail(studentRequestDto.getEmail());
        studentToSave.setRollNo(studentRequestDto.getRollNo());
        studentToSave.setSubject(studentRequestDto.getSubject());
        studentToSave.setDeleted(false);
        studentToSave.setCreatedAt(LocalDateTime.now());
        studentToSave.setUpdatedAt(LocalDateTime.now());

        return studentToSave;

    }

    private Boolean emailExist(Student student) {
        return studentRepository.existsByEmail(student.getEmail());
    }


    private CreateStudentResponseDto mapToDto(Student studentResponse) {

        CreateStudentResponseDto studentResponseDto = new CreateStudentResponseDto();

        studentResponseDto.setId(studentResponse.getId());
        studentResponseDto.setName(studentResponse.getName());
        studentResponseDto.setAge(studentResponse.getAge());
        studentResponseDto.setEmail(studentResponse.getEmail());
        studentResponseDto.setRollNo(studentResponse.getRollNo());
        studentResponseDto.setSubject(studentResponse.getSubject());
        studentResponseDto.setCreatedAt(studentResponse.getCreatedAt());
        studentResponseDto.setUpdatedAt(studentResponse.getUpdatedAt());
        studentResponseDto.setMessage("Student saved successfully");

        return studentResponseDto;

    }

    private UpdateStudentResponseDto mapToUpdateDto(Student student) {


        UpdateStudentResponseDto updatedStudent = new UpdateStudentResponseDto();

        updatedStudent.setId (student.getId());
        updatedStudent.setName(student.getName());
        updatedStudent.setAge(student.getAge());
        updatedStudent.setEmail(student.getEmail());
        updatedStudent.setRollNo(student.getRollNo());
        updatedStudent.setSubject(student.getSubject());
        updatedStudent.setMessage("Student saved successfully");

        return updatedStudent;

    }
}