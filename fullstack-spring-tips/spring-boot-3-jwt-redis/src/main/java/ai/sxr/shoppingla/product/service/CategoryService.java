package ai.sxr.shoppingla.product.service;

import ai.sxr.shoppingla.product.models.Category;
import ai.sxr.shoppingla.product.models.dto.CreateCategoryDto;
import ai.sxr.shoppingla.product.repositories.CategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(CreateCategoryDto createCategoryDto) {
        return categoryRepository.save(createCategoryDto.toCategory());
    }
//    @Caching(evict = {@CacheEvict("Category"), @CacheEvict(value="Category", key="#invId")})
//    public void deleteCategory(Long invId) {
//       categoryRepository.deleteById(invId);
//    }
//    @Caching(
//            evict = {@CacheEvict(value = "categoriesList", allEntries = true)},
//            put   = {@CachePut(value = "category", key = "#category.getId()")}
//    )
//    public Category updateCategory(Category category) {
//        return categoryRepository.update(category);
//    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> findAll() {
        return categoryRepository.findAllByIdGreaterThanOrderByIdDesc(0l);
    }

    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }
}