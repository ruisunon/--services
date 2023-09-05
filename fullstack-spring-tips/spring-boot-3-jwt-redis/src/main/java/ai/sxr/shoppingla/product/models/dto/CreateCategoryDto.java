package ai.sxr.shoppingla.product.models.dto;

import ai.sxr.shoppingla.product.models.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class CreateCategoryDto {
    @NotBlank(message = "This field is required")
    private String name;

    public Category toCategory() {
        Category category = new Category();

        category.setName(this.name);

        return category;
    }
}