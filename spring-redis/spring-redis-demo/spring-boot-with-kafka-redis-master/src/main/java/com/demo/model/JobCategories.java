package com.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jobCategories")
@JsonIgnoreProperties(value= {"jobs"})
public class JobCategories implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;

    @Column
    private String cateogryName;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Jobs> jobs = new ArrayList<>();

    public List<Jobs> getJobs() {
        return jobs;
    }

    public void setJobs(List<Jobs> jobs) {
        this.jobs = jobs;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getCateogryName() {
        return cateogryName;
    }

    public void setCateogryName(String cateogryName) {
        this.cateogryName = cateogryName;
    }

}
