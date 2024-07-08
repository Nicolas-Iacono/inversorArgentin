package com.ia.inv_arg.service.impl;

import com.ia.inv_arg.DTO.entrada.ComentEntradaDto;
import com.ia.inv_arg.DTO.salida.ComentSalidaDto;
import com.ia.inv_arg.entity.Article;
import com.ia.inv_arg.entity.Comment;
import com.ia.inv_arg.entity.User;
import com.ia.inv_arg.exception.ResourceNotFoundException;
import com.ia.inv_arg.repository.ArticleRepository;
import com.ia.inv_arg.repository.ComentRepository;
import com.ia.inv_arg.repository.UserRepository;
import com.ia.inv_arg.service.IComentService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ComentService implements IComentService {

    @Autowired
    private ComentRepository comentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @PostConstruct
    private void configureMapping() {
        modelMapper.typeMap(ComentEntradaDto.class, Comment.class)
                .addMappings(mapper -> {
                    mapper.map(ComentEntradaDto::getArticle, Comment::setId);
                    mapper.map(ComentEntradaDto::getUser, Comment::setId);
                });
        modelMapper.typeMap(Comment.class, ComentSalidaDto.class)
                .addMappings(mapper -> {
                    mapper.map(Comment::getId, ComentSalidaDto::setArticleId);
                    mapper.map(Comment::getId, ComentSalidaDto::setUserId);
                });
    }

    @Override
    public ComentSalidaDto crearComentario(ComentEntradaDto comentEntradaDto) throws ResourceNotFoundException {
        Article article = articleRepository.findById(comentEntradaDto.getArticle())
                .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado en la base de datos"));
        User user = userRepository.findById(comentEntradaDto.getUser())
                .orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado en la base de datos"));

        Comment comment = modelMapper.map(comentEntradaDto, Comment.class);
        comment.setArticle(article);
        comment.setUser(user);

        Comment savedComment = comentRepository.save(comment);

        return modelMapper.map(savedComment, ComentSalidaDto.class);
    }

    @Override
    public List<ComentSalidaDto> listarComentarios(Long id) {
        List<Comment> comments = comentRepository.findByArticleId(id);
        return comments.stream()
                .map(comment -> modelMapper.map(comment, ComentSalidaDto.class))
                .toList();
    }
}
