package ai.sxr.shoppingla.product.service;

import ai.sxr.shoppingla.product.models.Category;
import ai.sxr.shoppingla.product.models.Product;
import ai.sxr.shoppingla.product.models.dto.CreateProductDto;
import ai.sxr.shoppingla.product.models.dto.SearchProductDto;
import ai.sxr.shoppingla.product.repositories.CategoryRepository;
import ai.sxr.shoppingla.product.repositories.ProductRepository;
import ai.sxr.shoppingla.product.repositories.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Caching
public class ProductService {
    @PersistenceContext
    private EntityManager entityManager;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final TagRepository tagRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    public Product create(CreateProductDto createProductDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(createProductDto.getCategoryId());

        if (optionalCategory.isEmpty()) {
            throw new RuntimeException("The category not found");
        }

        Product product = createProductDto.toProduct();
        product.setCategory(optionalCategory.get());

        return productRepository.save(product);
    }

    @CachePut(value="products", unless="#result.length()<24")
    public List<Product> findAll() {
        return productRepository.findAllByIdGreaterThanOrderByIdDesc(0);
    }

    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> search(SearchProductDto searchProductDto) throws InterruptedException {
        Thread.sleep(3000);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> product = criteriaQuery.from(Product.class);

        Metamodel metamodel = entityManager.getMetamodel();
        EntityType<Product> productMetaModel = metamodel.entity(Product.class);

        Join<Product, Category> joinCategory = product.join(productMetaModel.getSingularAttribute("category", Category.class));

        List<Predicate> predicateList = new ArrayList<>();

        if (searchProductDto.getName() != null) {
            predicateList.add(
                    criteriaBuilder.like(product.get("name"), "%"+searchProductDto.getName().trim()+"%")
            );
        }

        if (searchProductDto.getCategory() != null) {
            predicateList.add(
                    criteriaBuilder.equal(joinCategory.get("name"), searchProductDto.getCategory())
            );
        }

        if (searchProductDto.getMinPrice() > 0 && searchProductDto.getMaxPrice() > 0) {
            predicateList.add(
                    criteriaBuilder.between(product.get("price"), searchProductDto.getMinPrice(), searchProductDto.getMaxPrice())
            );
        } else {
            if (searchProductDto.getMinPrice() > 0) {
                predicateList.add(
                        criteriaBuilder.gt(product.get("price"), searchProductDto.getMinPrice())
                );
            }

            if (searchProductDto.getMaxPrice() > 0) {
                predicateList.add(
                        criteriaBuilder.lt(product.get("price"), searchProductDto.getMaxPrice())
                );
            }
        }

        if (searchProductDto.getAvailable() != null) {
            predicateList.add(criteriaBuilder.equal(product.get("isAvailable"), Objects.equals(searchProductDto.getAvailable(), "yes") ? 1 : 0));
        }

        Predicate[] predicates = new Predicate[predicateList.size()];

        predicateList.toArray(predicates);

        if (!predicateList.isEmpty()) {
            criteriaQuery.where(predicates);
        }

        criteriaQuery.select(product).orderBy(criteriaBuilder.desc(product.get("createdAt")));
        // criteriaQuery.orderBy(criteriaBuilder.desc(product.get("createdAt")), criteriaBuilder.asc(product.get("price")));

        TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }
    @CacheEvict(value = "product", key = "#product.id")
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @CacheEvict(value = "product", key = "#id")
    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

    @CacheEvict(value = { "product", "products", "available_products" }, allEntries = true)
    public void deleteAll() {
        productRepository.deleteAll();
    }

    @Cacheable("available_products")
    public List<Product> findByIsAvailable(boolean isAvailable) {
        doLongRunningTask();

        return productRepository.findByIsAvailable(isAvailable);
    }
    private void doLongRunningTask() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}