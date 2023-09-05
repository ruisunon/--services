package ai.sxr.shoppingla.product.models.response;


import ai.sxr.shoppingla.product.models.Category;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;


@Setter
@Getter
@Accessors(chain = true)
public class CategoryListResponse {
    private List<Category> data;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CategoryListResponse(@JsonProperty("data") List<Category> data) {
        this.data = data;
    }
}