package com.rycoding.ecommerce.service.student;

import com.rycoding.ecommerce.dto.GeneralResponse;
import com.rycoding.ecommerce.dto.StudentDTO;

import java.util.List;

public interface StudentService {
    GeneralResponse addStudent(StudentDTO studentDto, Long userId);

    List<StudentDTO> getAllStudents();

    GeneralResponse updateStudent(Long studentId, StudentDTO studentDto);

    StudentDTO getStudentById(Long studentId);

    void deleteStudent(Long studentId);

    List<StudentDTO> searchStudentByName(String title);
}
