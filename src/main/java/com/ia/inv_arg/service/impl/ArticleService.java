package com.ia.inv_arg.service.impl;

import com.ia.inv_arg.DTO.entrada.ArticleDTO;
import com.ia.inv_arg.DTO.modificacion.ArticleModificacionEntradaDTO;
import com.ia.inv_arg.DTO.modificacion.UserModificacionEntradaDTO;
import com.ia.inv_arg.DTO.salida.ArticleAuthorSalidaDto;
import com.ia.inv_arg.DTO.salida.ArticleDtoSalida;
import com.ia.inv_arg.entity.Article;
import com.ia.inv_arg.entity.User;
import com.ia.inv_arg.exception.ResourceNotFoundException;
import com.ia.inv_arg.repository.ArticleRepository;
import com.ia.inv_arg.repository.UserRepository;
import com.ia.inv_arg.service.IArticleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService implements IArticleService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private ArticleRepository articleRepository;
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, ModelMapper modelMapper, UserRepository userRepository)
    {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }


    @Override
    public List<ArticleDtoSalida> listarArticulos() {
        List<Article> articles = articleRepository.findAllWithAuthors();

        List<ArticleDtoSalida> articuloSalidaDto = articles.stream()
                .map(article -> modelMapper.map(article, ArticleDtoSalida.class))
                .toList();

        return articuloSalidaDto;
    }
//    public List<ArticleDtoSalida> getLatestArticles() {
//        // Llamar al repositorio para obtener los últimos 4 artículos
//        List<Article> articles = articleRepository.findLatestArticles();
//        LOGGER.info("se obtuvo los 4 articulos" + articles);
//        // Mapear cada artículo a su correspondiente DTO de salida
//        List<ArticleDtoSalida> articleDtoSalidas = articles.stream()
//                .map(article -> modelMapper.map(article, ArticleDtoSalida.class))
//                .toList();
//
//        return articleDtoSalidas;
//    }

    public List<ArticleAuthorSalidaDto> getLatestArticles() {
        // Llamar al repositorio para obtener los últimos 4 artículos
        List<Article> articles = articleRepository.findLatestArticles(PageRequest.of(0, 4)).getContent();
        LOGGER.info("Se obtuvieron los 4 artículos: " + articles);

        // Mapear cada artículo a su correspondiente DTO de salida
        return articles.stream()
                .map(article -> {
                    ArticleAuthorSalidaDto dto = new ArticleAuthorSalidaDto();
                    dto.setId(article.getId());
                    dto.setTitle(article.getTitle());
                    dto.setSubtitle(article.getSubtitle());
                    dto.setParagraph(article.getParagraph());
                    dto.setImage(article.getImage());
                    dto.setPublicDate(article.getPublicDate());
                    dto.setEmailAuthor(article.getAuthor().getEmail());  // Asignación del email del autor
                    return dto;
                })
                .collect(Collectors.toList());
    }

//        // Mapear cada artículo a su correspondiente DTO de salida
//        return articles.stream()
//                .map(article -> modelMapper.map(article, ArticleDtoSalida.class))
//                .collect(Collectors.toList());
//    }

    @Override
    public ArticleDtoSalida crearArticulo(ArticleDTO articleDTO) throws ResourceNotFoundException {

        Long authorId = articleDTO.getUserAuthorDto().getId();
        User author = userRepository.findById(authorId)
                .orElseThrow(()-> new ResourceNotFoundException("Author no encontrado"));

        Article articuloEnCreacion = modelMapper.map(articleDTO, Article.class);
        articuloEnCreacion.setAuthor(author);

        Article articuloAPersistir = articleRepository.save(articuloEnCreacion);
        author.getEmail();
        ArticleDtoSalida articleDtoSalida = modelMapper.map(articuloAPersistir, ArticleDtoSalida.class);


        return articleDtoSalida;
    }

    @Override
    public ArticleDtoSalida buscarArticuloPorId(Long id) {
        Article article = articleRepository.findByIdWithAuthor(id)
                .orElseThrow(() -> new EntityNotFoundException("Article not found"));
        return modelMapper.map(article, ArticleDtoSalida.class);
    }

    @Override
    public ArticleDtoSalida actualizarArticulo(ArticleModificacionEntradaDTO articleModificacionEntradaDTO, Long id) throws ResourceNotFoundException {
        // Buscar el artículo por ID
        Article articleToUpdate = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el articulo que quieres modificar"));

        // Actualizar los campos del artículo
        articleToUpdate.setTitle(articleModificacionEntradaDTO.getTitle());
        articleToUpdate.setSubtitle(articleModificacionEntradaDTO.getSubtitle());
        articleToUpdate.setImage(articleModificacionEntradaDTO.getImage());
        articleToUpdate.setParagraph(articleModificacionEntradaDTO.getParagraph());

        // Guardar el artículo actualizado en la base de datos
        Article updatedArticle = articleRepository.save(articleToUpdate);

        // Mapear el artículo actualizado a DTO de salida
        ArticleDtoSalida articleDtoSalida = modelMapper.map(updatedArticle, ArticleDtoSalida.class);
        return articleDtoSalida;
    }


    @Transactional
    @Override
    public void eliminarArticulo(Long id) throws ResourceNotFoundException {
        System.out.println("Eliminando artículo con ID: " + id); // Agrega esta línea para depuración
        Optional<Article> articleOptional = articleRepository.findById(id);
        if(articleOptional.isPresent()){
            Article article = articleOptional.get();
            System.out.println("Artículo encontrado: " + articleOptional); // Agrega esta línea para depuraciónX
            articleRepository.save(article);
            articleRepository.deleteById(id);
            System.out.println("Artículo Eliminado: " + article);
        }else{
            throw new ResourceNotFoundException("User not found with id: " + id);

        }

    }



    private void configureMapping(){
        modelMapper.typeMap(ArticleDTO.class, Article.class)
                .addMappings(mapper -> mapper.map(ArticleDTO::getUserAuthorDto, Article::setAuthor));
        modelMapper.typeMap(Article.class, ArticleDtoSalida.class)
                .addMappings(mapper -> mapper.map(Article::getAuthor, ArticleDtoSalida::setAuthorSalidaDto));
        modelMapper.typeMap(ArticleModificacionEntradaDTO.class, Article.class)
                .addMappings(mapper -> mapper.map(ArticleModificacionEntradaDTO::getUserModificacionEntradaDTOAuthor, Article::setAuthor ));
    }
}
