package com.rycoding.ecommerce.controller;

import com.rycoding.ecommerce.dto.TeacherDTO;
import com.rycoding.ecommerce.entities.student.Teacher;
import com.rycoding.ecommerce.service.student.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TeacherController {
    final private TeacherService teacherService;

    @GetMapping(params = { "page", "size", "sortBy" })
    public ResponseEntity<List<TeacherDTO>> getAllTeachers(
            @RequestParam(name="page", defaultValue="0", required = true) Integer pageNo,
            @RequestParam(name="size", defaultValue="10") Integer pageSize,
            @RequestParam(name="sort",defaultValue="id") String sortBy,
            @RequestParam(defaultValue = "desc") boolean desc
    ){
        List<TeacherDTO> list = teacherService.getAllTeachers(pageNo, pageSize, sortBy);
        return new ResponseEntity<List<TeacherDTO>>(list, new HttpHeaders(), HttpStatus.OK);
    }
}
