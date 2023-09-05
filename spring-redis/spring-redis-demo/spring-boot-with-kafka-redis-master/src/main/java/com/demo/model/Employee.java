package com.demo.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "employee")
@JsonIgnoreProperties(value= {"jobs"})
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employee_id;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String dob;
    @Column
    private String phoneNumber;
    @Column
    private String role;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "employee_jobs", joinColumns = {@JoinColumn(name = "employee_fk")}, inverseJoinColumns = {@JoinColumn(name = "jobs_fk")})
    private Set<Jobs> jobs = new HashSet<>();

    public Set<Jobs> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Jobs> jobs) {
        this.jobs = jobs;
    }

    public Long getEmployee_id() {
        return employee_id;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
