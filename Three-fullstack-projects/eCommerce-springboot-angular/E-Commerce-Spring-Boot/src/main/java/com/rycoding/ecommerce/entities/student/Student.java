package com.rycoding.ecommerce.entities.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rycoding.ecommerce.dto.StudentDTO;
import com.rycoding.ecommerce.entities.Book;
import com.rycoding.ecommerce.entities.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String motherName;

    private String fatherName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_book", joinColumns = @JoinColumn(name = "student_id"))
    private List<Book> bookList = new ArrayList<Book>();
    public StudentDTO getStudentDTO() {
        StudentDTO studentDto = new StudentDTO();
        studentDto.setId(id);
        studentDto.setName(name);
        studentDto.setFatherName(fatherName);
        studentDto.setMotherName(motherName);
        studentDto.setUserid(user.getId());
        studentDto.setPostedBy(user.getFirstname());
        return studentDto;
    }
}