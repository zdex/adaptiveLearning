package com.adap.learn.service;

import com.adap.learn.dto.StudentDto;
import com.adap.learn.model.Student;
import com.adap.learn.model.User;
import com.adap.learn.repository.StudentRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public List<StudentDto> saveStudents(List<StudentDto> studentDtos, User parent) {
        List<Student> students = studentDtos.stream()
                .map(dto -> Student.builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .age(dto.getAge())
                        .gradeLevel(dto.getGrade().toString())
                         .build())
                .collect(Collectors.toList());

        studentRepository.saveAll(students);

        // Return the saved DTOs (or a confirmation)
        return studentDtos;
    }

    /**
     * Retrieves a single Student profile by its ID, ensuring it belongs to the given parent User.
     * This method enforces security by checking the parent ID.
     *
     * @param studentId The ID of the student to retrieve.
     * @param parent The authenticated User object (the parent/owner).
     * @return The Student entity.
     * @throws RuntimeException if the student is not found or does not belong to the parent.
     */
    public Student getStudentByIdAndParent(Long studentId, User parent) {
        return studentRepository.findByStudentIdAndUser_UserId(studentId, parent.getUserId())
                .orElseThrow(() -> new RuntimeException("Student not found or unauthorized access."));
    }

    // Utility for DTO mapping (optional, but good practice)
    private StudentDto mapToDto(Student student) {
        return StudentDto.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .age(student.getAge())
                .grade(Integer.parseInt(student.getGradeLevel()))
                .build();
    }
}