package com.microservices.products.DAOs;

import com.microservices.products.Models.SubCategory;

import java.util.List;

public interface SubCategoryDao {

    List<SubCategory> GetAll();
    SubCategory GetById(Integer id);
    SubCategory Patch(SubCategory product);
    SubCategory Save(SubCategory prod);
    int Delete(Integer id);
}
