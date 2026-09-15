package org.example.crudapp.service;

import org.example.crudapp.dto.CreateStudentRequestDto;
import org.example.crudapp.dto.CreateStudentResponseDto;
import org.example.crudapp.dto.UpdateStudentRequestDto;
import org.example.crudapp.dto.UpdateStudentResponseDto;
import org.example.crudapp.model.Student;
import org.example.crudapp.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentRequestDTO) {
        Student student = mapToEntity(studentRequestDTO);


        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        Student studentResp = studentRepository.save(student);

        return mapToDto(studentResp);

    }

    public CreateStudentResponseDto getStudent(Long id) {
        Optional<Student> studentRes = studentRepository.findByIdAndDeletedIsFalse(id);

//        if(studentRes.isPresent()) {
//            return mapToDto(studentRes.get());
//        }
//
//        return null;

        return mapToDto(studentRes.get());
    }

    public List<CreateStudentResponseDto> getAllStudents() {

        List<Student> studentList = studentRepository.findByDeletedIsFalse();


//        return studentRepository.findAll();
        return studentList.stream().map(this::mapToDto).toList();
    }

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentReq) {

        Optional<Student> studentToUpdate = studentRepository.findByIdAndDeletedIsFalse(id);

        if (studentToUpdate.isEmpty()) {
            return null;
        }
        Student studentToSave = studentToUpdate.get();

        studentToSave.setName(studentReq.getName());
        studentToSave.setAge(studentReq.getAge());
        studentToSave.setRollNo(studentReq.getRollNo());
        studentToSave.setSubject(studentReq.getSubject());
        studentToSave.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(studentToSave);

        return mapToUpdateDto(studentToSave);

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
            if(student.isDeleted()) {
                return true;
            }
            student.setUpdatedAt(LocalDateTime.now());
            student.setDeleted(true);
            studentRepository.save(student);
            return true;
        }
        return false;

    }

    private Student mapToEntity(CreateStudentRequestDto studentRequestDto) {
        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setAge(studentRequestDto.getAge());
        student.setEmail(studentRequestDto.getEmail());
        student.setRollNo(studentRequestDto.getRollNo());
        student.setSubject(studentRequestDto.getSubject());

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



}
