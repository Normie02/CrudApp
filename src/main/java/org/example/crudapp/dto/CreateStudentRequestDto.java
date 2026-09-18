package org.example.crudapp.dto;

import jakarta.validation.constraints.*;

public class CreateStudentRequestDto {

    @Email(message = "Invalid email")
    private String email;

    @NotBlank
    @Size(min=2,max=50,message = "Student name must be within 2 - 50 character long")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 18)
    private Integer age ;

    @NotNull(message = "Must not empty")
    private Integer rollNo;

    @NotBlank(message = "Subject is required")
    private String subject;

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setRollNo(Integer rollNo) {
        this.rollNo = rollNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
