package org.example.crudapp.dto;

import java.time.LocalDateTime;

public class UpdateStudentResponseDto {


    private Long id;
    private String name;
    private int age ;
    private int rollNo;
    private String subject;
    private String message;
    private String Email;
    private LocalDateTime updatedAt;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getRollNo() {
        return rollNo;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return Email;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setAge(int age) {
        this.age = age;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }


    public void setSubject(String subject) {
        this.subject = subject;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
