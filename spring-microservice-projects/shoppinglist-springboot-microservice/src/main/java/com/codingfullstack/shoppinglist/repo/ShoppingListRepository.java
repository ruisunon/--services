package com.codingfullstack.shoppinglist.repo;

import org.springframework.data.repository.CrudRepository;

import com.codingfullstack.shoppinglist.model.ShoppingItem;

public interface ShoppingListRepository extends CrudRepository<ShoppingItem, String> {
}
