package ai.sxr.shoppingla.product.service;

import ai.sxr.shoppingla.product.repositories.TagRepository;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@Caching
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

}
