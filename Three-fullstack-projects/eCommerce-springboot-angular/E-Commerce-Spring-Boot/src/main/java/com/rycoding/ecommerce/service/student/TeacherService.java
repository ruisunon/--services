package com.rycoding.ecommerce.service.student;

import com.rycoding.ecommerce.dto.GeneralResponse;
import com.rycoding.ecommerce.dto.TeacherDTO;

import java.util.List;

public interface TeacherService {
    GeneralResponse addTeacher(Long userId, TeacherDTO teacherDto);

    List<TeacherDTO> getAllTeachers(Integer pageNo, Integer pageSize, String sortBy);

    void deleteTeacher(Long teacherId);

    GeneralResponse updateTeacher(Long teacherId, TeacherDTO teacherDto);

    TeacherDTO getTeacherById(Long teacherId);
}
