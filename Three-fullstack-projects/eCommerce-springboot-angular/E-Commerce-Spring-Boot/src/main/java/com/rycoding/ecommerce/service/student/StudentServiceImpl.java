package com.rycoding.ecommerce.service.student;

import com.rycoding.ecommerce.dto.GeneralResponse;
import com.rycoding.ecommerce.dto.StudentDTO;
import com.rycoding.ecommerce.entities.User;
import com.rycoding.ecommerce.entities.student.Student;
import com.rycoding.ecommerce.repository.UserRepository;
import com.rycoding.ecommerce.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private UserRepository userRepo;

    @Override
    @CacheEvict(value = "student", key = "#student.id")
    public GeneralResponse addStudent(StudentDTO studentDto, Long userId) {
        GeneralResponse response = new GeneralResponse();
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            Student student = new Student();
            student.setName(studentDto.getName());
            student.setFatherName(studentDto.getFatherName());
            student.setMotherName(studentDto.getMotherName());
            student.setUser(optionalUser.get());
            studentRepository.save(student);
            response.setMessage("Student created.");
            response.setData(HttpStatus.CREATED);
        } else {
            response.setMessage("User not exist!");
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    @Cacheable("students")
    public List<StudentDTO> getAllStudents() {
        doLongRunningTask();
        return studentRepository.findAll().stream().map(Student::getStudentDTO).collect(Collectors.toList());
    }

    @Override
    public GeneralResponse updateStudent(Long studentId, StudentDTO studentDto) {
        GeneralResponse response = new GeneralResponse();
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setMotherName(studentDto.getMotherName());
            student.setFatherName(studentDto.getFatherName());
            student.setName(studentDto.getName());
            studentRepository.save(student);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Student updated successfully!");
        } else {
            response.setMessage("Student not found");
            response.setStatus(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Override
    public StudentDTO getStudentById(Long studentId) {
        StudentDTO singleStudentDto = new StudentDTO();
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()){
            singleStudentDto=optionalStudent.get().getStudentDTO();
        }
        return singleStudentDto;
    }

    @Override
    @CacheEvict(value = "student", key = "#id")
    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    @Cacheable("students")
    public List<StudentDTO> searchStudentByName(String title) {
        return studentRepository.findAllByNameContaining(title).stream().map(Student::getStudentDTO).collect(Collectors.toList());
    }

    @CacheEvict(value = { "student", "students", "new_students" }, allEntries = true)
    public void deleteAll() {
        studentRepository.deleteAll();
    }

    private void doLongRunningTask() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
