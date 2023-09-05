package by.andd3dfx.templateapp.services.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.andd3dfx.templateapp.dto.ArticleDto;
import by.andd3dfx.templateapp.mappers.ArticleMapper;
import by.andd3dfx.templateapp.persistence.dao.ArticleRepository;
import by.andd3dfx.templateapp.persistence.entities.Article;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    ArticleRepository articleRepository;
    @Mock
    ArticleMapper articleMapper;
    @InjectMocks
    ArticleService service;

    @Test
    void create() {
        final ArticleDto articleDto = ArticleDto.builder().build();
        final Article article = Article.builder().build();
        final Article savedArticle = Article.builder()
                .id(232L)
                .build();
        when(articleMapper.toArticle(articleDto)).thenReturn(article);
        when(articleRepository.save(article)).thenReturn(savedArticle);
        final ArticleDto resultArticle = ArticleDto.builder()
                .id("123")
                .build();
        when(articleMapper.toArticleDto(savedArticle)).thenReturn(resultArticle);

        ArticleDto result = service.create(articleDto);

        assertThat(result, is(resultArticle));
    }

    @Test
    void get() {
        final String ID = "345";
        final Article article = Article.builder()
                .id(Long.valueOf(ID))
                .build();
        when(articleRepository.findById(ID)).thenReturn(Optional.of(article));
        final ArticleDto resultArticle = ArticleDto.builder()
                .id(ID)
                .build();
        when(articleMapper.toArticleDto(article)).thenReturn(resultArticle);

        ArticleDto result = service.get(ID);

        assertThat(result, is(resultArticle));
    }

    @Test
    void delete() {
        final String ID = "345";

        service.delete(ID);

        verify(articleRepository).deleteById(ID);
    }

    @Test
    void getAll() {
        final Article article1 = Article.builder()
                .id(Long.valueOf("345"))
                .build();
        final Article article2 = Article.builder()
                .id(Long.valueOf("347"))
                .build();
        when(articleRepository.findAll()).thenReturn(List.of(article1, article2));
        final ArticleDto articleDto1 = ArticleDto.builder().id("345").build();
        final ArticleDto articleDto2 = ArticleDto.builder().id("347").build();
        when(articleMapper.toArticleDto(article1)).thenReturn(articleDto1);
        when(articleMapper.toArticleDto(article2)).thenReturn(articleDto2);

        List<ArticleDto> result = service.getAll();

        assertThat(result.size(), is(2));
        assertThat(result, hasItems(articleDto1, articleDto2));
    }
}
