package com.ia.inv_arg.repository;

import com.ia.inv_arg.DTO.salida.ArticleDtoSalida;
import com.ia.inv_arg.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Modifying
    @Query("DELETE FROM Article a WHERE a.id = :id")
    void deleteById(@Param("id") Long id);

    @Query("SELECT a FROM Article a JOIN FETCH a.author")
    List<Article> findAllWithAuthors();

    @Query("SELECT a FROM Article a LEFT JOIN FETCH a.author WHERE a.id = :id")
    Optional<Article> findByIdWithAuthor(@Param("id") Long id);

//    @Query("SELECT a FROM Article a ORDER BY a.publicDate DESC")
//    List<Article> findLatestArticles(Pageable pageable);

    @Query("SELECT a FROM Article a ORDER BY a.publicDate DESC")
    Page<Article> findLatestArticles(Pageable pageable);



}
