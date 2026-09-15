package org.example.crudapp.repository;

import org.example.crudapp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {


    Optional<Student> findByIdAndDeletedIsFalse(Long id);
//    Optional<Student> findByIdAndDeletedIsTrue(Long id);

    List<Student> findByDeletedIsFalse();

}