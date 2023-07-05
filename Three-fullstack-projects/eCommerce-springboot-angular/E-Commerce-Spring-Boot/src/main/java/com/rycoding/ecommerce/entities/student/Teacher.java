package com.rycoding.ecommerce.entities.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rycoding.ecommerce.dto.TeacherDTO;
import com.rycoding.ecommerce.dto.UserDTO;
import com.rycoding.ecommerce.entities.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Entity
@Table(name="teachers")
@Data
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String department;
    private String title;
    @Column
    private double salary;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
    public TeacherDTO getTeacherDto(){
        TeacherDTO teacherDto = new TeacherDTO();
        teacherDto.setId(id);
        teacherDto.setTitle(title);
        teacherDto.setName(name);
        teacherDto.setDepartment(department);
        teacherDto.setUserId(user.getId());
        teacherDto.setPostedBy(user.getUsername());
        return teacherDto;
    }

}
