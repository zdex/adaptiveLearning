package com.adap.learn.service;

import com.adap.learn.dto.StudentDTO;
import com.adap.learn.entity.StudentEntity;
import com.adap.learn.entity.ParentEntity;
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
    public List<StudentDTO> saveStudents(List<StudentDTO> studentDtos, ParentEntity parent) {
        List<StudentEntity> students = studentDtos.stream()
                .map(dto -> StudentEntity.builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .age(dto.getAge())
                        .grade(dto.getGrade().toString())
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
    public StudentEntity getStudentByIdAndParent(Long studentId, ParentEntity parent) {
        return studentRepository.findByStudentIdAndParent_UserId(studentId, parent.getUserId())
                .orElseThrow(() -> new RuntimeException("Student not found or unauthorized access."));
    }

    // Utility for DTO mapping (optional, but good practice)
    private StudentDTO mapToDto(StudentEntity student) {
        return StudentDTO.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .age(student.getAge())
                .grade(student.getGrade())
                .build();
    }
}