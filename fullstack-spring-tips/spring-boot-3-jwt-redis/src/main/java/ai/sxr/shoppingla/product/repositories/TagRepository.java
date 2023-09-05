package ai.sxr.shoppingla.product.repositories;
import java.util.List;

import ai.sxr.shoppingla.product.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findTagsByName(String nameLike);
}
