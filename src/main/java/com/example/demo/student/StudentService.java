package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final  StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> existingStudent = studentRepository.findStudentByEmail(student.getEmail());

        if (existingStudent.isPresent()){
            throw new IllegalStateException("Email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);

        if(!exists) {
            throw new IllegalStateException("Student with id " + studentId + " does not exist.");
        }
        studentRepository.deleteById(studentId);
    }

    public Optional<Student> getStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);

        if(!exists) {
            throw new IllegalStateException("Student with id " + studentId + " does not exist.");
        }
        Optional<Student> existingStudent = studentRepository.findById(studentId);
        return existingStudent;
    }

    public void updateStudent(Long studentId, String name, String email) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id " + studentId + " does not exist."
                ));

        if(name != null && name.length() > 0 && !Objects.equals(existingStudent.getName(), name)) {
            existingStudent.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(existingStudent.getEmail(), email)) {
            existingStudent.setEmail(email);
        }
    }
}
