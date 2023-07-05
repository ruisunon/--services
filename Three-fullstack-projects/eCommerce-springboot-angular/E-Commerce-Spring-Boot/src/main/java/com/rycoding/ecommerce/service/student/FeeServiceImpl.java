package com.rycoding.ecommerce.service.student;

import com.rycoding.ecommerce.dto.FeeDTO;
import com.rycoding.ecommerce.dto.GeneralResponse;
import com.rycoding.ecommerce.entities.student.Fee;
import com.rycoding.ecommerce.entities.student.Student;
import com.rycoding.ecommerce.repository.student.FeeRepository;
import com.rycoding.ecommerce.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeeServiceImpl implements FeeService{


    final private FeeRepository feeRepository;

    final private StudentRepository studentRepository;

    @Override
    public GeneralResponse payFee(Long studentId, FeeDTO feeDto) {
        GeneralResponse response = new GeneralResponse();
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isPresent()){
            Fee fee = new Fee();
            fee.setAmount(feeDto.getAmount());
            fee.setStudent(optionalStudent.get());
            fee.setUser(optionalStudent.get().getUser());
            feeRepository.save(fee);
            response.setMessage("Fee paid successfully!");
            response.setStatus(HttpStatus.CREATED);
        } else {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Student no found");
        }
        return response;
    }
}
