package com.ia.inv_arg.service;

import com.ia.inv_arg.DTO.entrada.ArticleDTO;
import com.ia.inv_arg.DTO.entrada.UserDTO;
import com.ia.inv_arg.DTO.modificacion.ArticleModificacionEntradaDTO;
import com.ia.inv_arg.DTO.salida.ArticleAuthorSalidaDto;
import com.ia.inv_arg.DTO.salida.ArticleDtoSalida;
import com.ia.inv_arg.DTO.salida.UserDtoSalida;
import com.ia.inv_arg.entity.Article;
import com.ia.inv_arg.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IArticleService {

    List<ArticleDtoSalida> listarArticulos();

    ArticleDtoSalida crearArticulo(ArticleDTO articleDTO) throws ResourceNotFoundException;

    ArticleDtoSalida buscarArticuloPorId(Long id);

    ArticleDtoSalida actualizarArticulo(ArticleModificacionEntradaDTO articleModificacionEntradaDTO, Long id) throws ResourceNotFoundException;
    void eliminarArticulo(Long id) throws ResourceNotFoundException;

    List<ArticleAuthorSalidaDto> getLatestArticles();

}
