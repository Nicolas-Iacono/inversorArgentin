package com.ia.inv_arg.repository;

import com.ia.inv_arg.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentRepository  extends JpaRepository<Comment,Long> {
    List<Comment> findByArticleId(Long id);
}
