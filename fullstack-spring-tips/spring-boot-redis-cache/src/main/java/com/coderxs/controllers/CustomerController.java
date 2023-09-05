package com.coderxs.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coderxs.models.Customer;
import com.coderxs.services.CustomerService;


@RestController
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@GetMapping(value = "/customers")
	public ResponseEntity<Object> getAllCustomers() {
		List<Customer> customers = this.customerService.getAll();
		return ResponseEntity.ok(customers);
	}

	@Operation(summary = "Get a book by its id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the book",
					content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class))}),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Book not found", content = @Content)}) // @formatter:on
	@GetMapping("/{id}")
	public Customer findById(@Parameter(description = "id of book to be searched") @PathVariable long id) {
		return this.customerService.getCustomerById(id);
	}

	@GetMapping(value = "/customers/{id}")
	public ResponseEntity<Object> getCustomerById(@PathVariable("id") String id) {
		Long _id = Long.valueOf(id);
		Customer customer = this.customerService.getCustomerById(_id);
		return ResponseEntity.ok(customer);
	}

	@PostMapping(value = "/customers")
	public ResponseEntity<Object> addCustomer(@RequestBody Customer customer) {
		Customer created = this.customerService.add(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping(value = "/customers")
	public ResponseEntity<Object> updateCustomer(@RequestBody Customer customer) {
		Customer updated = this.customerService.update(customer);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping(value = "/customers/{id}")
	public ResponseEntity<Object> deleteCustomerById(@PathVariable("id") String id) {
		Long _id = Long.valueOf(id);
		this.customerService.delete(_id);
		return ResponseEntity.ok().build();
	}
}
