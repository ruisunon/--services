package ai.sxr.shoppingla.product.models.dto;

import ai.sxr.shoppingla.product.models.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class CreateProductDto {
    @Positive(message = "This field is required")
    private long categoryId;

    @NotBlank(message = "This field is required")
    private String name;

    private String description;

    @Positive(message = "Must be greater than 0")
    private float price;

    private int isAvailable;

    private String categoryName;

    public Product toProduct() {
        Product product = new Product();

        product.setName(this.name)
                .setDescription(description)
                .setPrice(price)
                .setAvailable(isAvailable == 1);

        return product;
    }
}