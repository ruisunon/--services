package by.andd3dfx.templateapp.services;

import by.andd3dfx.templateapp.dto.ArticleDto;
import by.andd3dfx.templateapp.dto.ArticleUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IArticleService {

    ArticleDto create(ArticleDto articleDto);

    ArticleDto get(String id);

    void update(String id, ArticleUpdateDto articleUpdateDto);

    void delete(String id);

    List<ArticleDto> getAll();
}
