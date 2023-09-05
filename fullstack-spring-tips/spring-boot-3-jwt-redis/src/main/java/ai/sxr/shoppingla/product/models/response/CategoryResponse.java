package ai.sxr.shoppingla.product.models.response;


import ai.sxr.shoppingla.product.models.Category;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryResponse {
    private Category data;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CategoryResponse(@JsonProperty("data") Category data) {
        this.data = data;
    }
}