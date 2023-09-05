package com.codingfullstack.shoppinglist.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="shopping-items")
public class ShoppingItem {
	
	@Id
	private String id;
	
    @NotEmpty(message = "{shopping.list.mandatory}")
    @Pattern(regexp = "[a-zA-Z0-9]*", message = "{shopping.list.alphanumeric}")
	private String name;
}
