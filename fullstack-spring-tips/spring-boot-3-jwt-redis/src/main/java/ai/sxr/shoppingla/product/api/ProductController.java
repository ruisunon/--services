package ai.sxr.shoppingla.product.api;

import ai.sxr.shoppingla.product.models.Product;
import ai.sxr.shoppingla.product.models.dto.CreateProductDto;
import ai.sxr.shoppingla.product.models.dto.SearchProductDto;
import ai.sxr.shoppingla.product.models.response.ProductListResponse;
import ai.sxr.shoppingla.product.models.response.ProductResponse;
import ai.sxr.shoppingla.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductDto createProductDto) {
        Product product = productService.create(createProductDto);

        return ResponseEntity.ok(new ProductResponse(product));
    }

    @GetMapping
    public ResponseEntity<ProductListResponse> getAll() {
        List<Product> categories = productService.findAll();

        return ResponseEntity.ok(new ProductListResponse(categories));
    }

    @GetMapping("/search")
    public ResponseEntity<ProductListResponse> search(@Valid SearchProductDto searchProductDto) throws InterruptedException {
        List<Product> categories = productService.search(searchProductDto);

        return ResponseEntity.ok(new ProductListResponse(categories));
    }
}