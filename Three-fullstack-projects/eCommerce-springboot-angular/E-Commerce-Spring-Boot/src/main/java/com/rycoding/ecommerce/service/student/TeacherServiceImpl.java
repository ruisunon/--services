package com.rycoding.ecommerce.service.student;

import com.rycoding.ecommerce.dto.GeneralResponse;
import com.rycoding.ecommerce.dto.TeacherDTO;
import com.rycoding.ecommerce.entities.User;
import com.rycoding.ecommerce.entities.student.Teacher;
import com.rycoding.ecommerce.repository.UserRepository;
import com.rycoding.ecommerce.repository.student.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private UserRepository userRepo;

    private TeacherRepository teacherRepository;

    @Override
    public GeneralResponse addTeacher(Long userId, TeacherDTO teacherDto) {
        GeneralResponse response = new GeneralResponse();
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            Teacher teacher = new Teacher();
            teacher.setName(teacherDto.getName());
            teacher.setDepartment(teacherDto.getDepartment());
            teacher.setUser(optionalUser.get());
            teacherRepository.save(teacher);
            response.setStatus(HttpStatus.CREATED);
            response.setMessage("Teacher is created.");
        } else {
            response.setMessage("User not exist");
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return response;
    }


    @Retryable(value = HttpStatusCodeException.class, maxAttempts = 3, backoff = @Backoff(3000), exclude =
            HttpClientErrorException.class)

    public List<TeacherDTO> getAllTeachers(Integer pageNo, Integer pageSize, String sortBy) {
        Sort nameSort = Sort.by("name");
        Sort salarySort = Sort.by("salary").ascending();
        Sort sortBySort = Sort.by(sortBy);
        Sort groupBySort = nameSort.and(salarySort).and(sortBySort);
        Pageable paging = PageRequest.of(pageNo, pageSize, groupBySort);
        Page<Teacher> pagedResult = teacherRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent().stream().map(Teacher::getTeacherDto).collect(Collectors.toList());
        } else {
            return new ArrayList<TeacherDTO>();
        }
    }
    @Recover
    public String recover(HttpServerErrorException exception) {
        return "Please try after some time!!";
    }

    @Override
    public void deleteTeacher(Long teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    @Override
    public GeneralResponse updateTeacher(Long teacherId, TeacherDTO teacherDto) {
        GeneralResponse response = new GeneralResponse();
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
        if (optionalTeacher.isPresent()) {
            Teacher teacher = optionalTeacher.get();
            teacher.setDepartment(teacherDto.getDepartment());
            teacher.setName(teacherDto.getName());
            teacherRepository.save(teacher);
            response.setMessage("Teacher updated successfully!");
            response.setStatus(HttpStatus.OK);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Teacher not found!");
        }
        return response;
    }

    @Override
    public TeacherDTO getTeacherById(Long teacherId) {
        TeacherDTO singleTeacherDto = new TeacherDTO();
        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);
        if (optionalTeacher.isPresent()) {
            singleTeacherDto=optionalTeacher.get().getTeacherDto();
        }
        return singleTeacherDto;
    }
}
