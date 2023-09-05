package com.codingfullstack.shoppinglist.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingfullstack.shoppinglist.model.ShoppingItem;
import com.codingfullstack.shoppinglist.repo.ShoppingListRepository;

@RestController
@RequestMapping("/")
public class ShoppingListController {

	@Autowired
	public ShoppingListRepository shoppingRepo;

	@GetMapping
	public Iterable<ShoppingItem> getShoopingList() {
		return shoppingRepo.findAll();
	}

	@GetMapping("/{id}")
	public ShoppingItem getItem(@PathVariable String id) {
		return shoppingRepo.findById(id).get();
	}

	@PutMapping
	public ShoppingItem addShoppingList(@Valid @RequestBody ShoppingItem item) {
		return shoppingRepo.save(item);
	}

	@DeleteMapping("/{id}")
	public void deleteItem(@PathVariable String id) {
		shoppingRepo.deleteById(id);
	}
}