package com.ia.inv_arg;

import com.ia.inv_arg.entity.Article;
import com.ia.inv_arg.repository.ArticleRepository;
import com.ia.inv_arg.service.impl.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;

    @MockBean
    private ArticleRepository articleRepository;

    @Test
    public void testEliminarArticulo() {
        Long articleId = 1L;
        Article article = new Article();
        article.setId(articleId);
        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        articleService.eliminarArticulo(articleId);

        verify(articleRepository, times(1)).deleteById(articleId);
        verify(articleRepository, times(1)).findById(articleId);
    }

    @Test
    public void testEliminarArticuloNotFound() {
        Long articleId = 1L;
        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        articleService.eliminarArticulo(articleId);

        verify(articleRepository, times(0)).deleteById(articleId);
        verify(articleRepository, times(1)).findById(articleId);
    }

}
