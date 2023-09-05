package ai.sxr.shoppingla.product.models.response;

import ai.sxr.shoppingla.product.models.Product;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductResponse {
    private Product data;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductResponse(@JsonProperty("data") Product data) {
        this.data = data;
    }
}