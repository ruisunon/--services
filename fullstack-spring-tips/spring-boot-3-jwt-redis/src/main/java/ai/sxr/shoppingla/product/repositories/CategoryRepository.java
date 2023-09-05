package ai.sxr.shoppingla.product.repositories;

import ai.sxr.shoppingla.product.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIdGreaterThanOrderByIdDesc(Long id);

    Optional<Category> findById(long id);

    Optional<Category> findByName(String name);

    //Category update(Category category);
}