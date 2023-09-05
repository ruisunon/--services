package ai.sxr.shoppingla.product.repositories;

import ai.sxr.shoppingla.product.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByIdGreaterThanOrderByIdDesc(long id);

    Optional<Product> findByName(String name);
    List<Product> findByIsAvailable(boolean available);
    List<Product> findByDescriptionContaining(String feature);

    List<Product> findProductsByTagsId(Long tagId);

    @Query(value = "SELECT * FROM products", nativeQuery = true)
    List<Product> findAll();

    @Query(value = "SELECT * FROM products t WHERE t.isAvailable=?1", nativeQuery = true)
    List<Product> findByPublished(boolean isPublished);

    @Query(value = "SELECT * FROM products t WHERE t.name LIKE %?1%", nativeQuery = true)
    List<Product> findByNameLike(String name);

    @Query(value = "SELECT * FROM products t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', ?1,'%'))", nativeQuery = true)
    List<Product> findByNameLikeCaseInsensitive(String name);

    @Query(value = "SELECT * FROM products t WHERE t.created_at >= ?1", nativeQuery = true)
    List<Product> findByDateGreaterThanEqual(Date date);

    @Query(value = "SELECT * FROM products t WHERE t.created_at BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Product> findByDateBetween(Date start, Date end);

//    @Query(value = "SELECT * FROM products t WHERE t.is_available=:isAvailable AND t.level BETWEEN :start AND :end", nativeQuery = true)
//    List<Product> findByLevelBetween(@Param("start") int start, @Param("end") int end, @Param("isPublished") boolean isAvailable);

    @Query(value = "SELECT * FROM products t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword,'%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword,'%'))", nativeQuery = true)
    List<Product> findByNameContainingOrDescriptionContainingCaseInsensitive(String keyword);

    @Query(value = "SELECT * FROM products t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :title,'%')) AND t.is_available=:isPublished", nativeQuery = true)
    List<Product> findByNameContainingCaseInsensitiveAndIsAvailable(String title, boolean isPublished);

    @Transactional
    @Modifying
    @Query(value = "UPDATE products SET is_available=true WHERE id=?1", nativeQuery = true)
    int publishProduct(Long id);

    @Query(value = "SELECT * FROM products t WHERE t.is_available=true ORDER BY t.created_at DESC", nativeQuery = true)
    List<Product> findAllIsAvailableOrderByCreatedDesc();

    @Query(value = "SELECT * FROM products t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', ?1,'%'))", nativeQuery = true)
    Page<Product> findByNameLike(String name, Pageable pageable);

    @Query(value = "SELECT * FROM products t WHERE t.is_available=?1", nativeQuery = true)
    Page<Product> findByIsAvailable(boolean isPublished, Pageable pageable);

    @Query(value = "SELECT * FROM products", nativeQuery = true)
    Page<Product> findAllWithPagination(Pageable pageable);
}