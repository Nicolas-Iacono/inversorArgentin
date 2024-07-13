package com.ia.inv_arg.controller;

import com.ia.inv_arg.DTO.entrada.ArticleDTO;
import com.ia.inv_arg.DTO.modificacion.ArticleModificacionEntradaDTO;
import com.ia.inv_arg.DTO.salida.ArticleAuthorSalidaDto;
import com.ia.inv_arg.DTO.salida.ArticleDtoSalida;
import com.ia.inv_arg.entity.Article;
import com.ia.inv_arg.entity.User;
import com.ia.inv_arg.exception.ResourceNotFoundException;
import com.ia.inv_arg.service.IArticleService;
import com.ia.inv_arg.service.IUserService;
import com.ia.inv_arg.service.impl.ArticleService;
import com.ia.inv_arg.service.impl.UserService;
import com.ia.inv_arg.utils.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articulos")
@PreAuthorize("denyAll()")
public class ArticleController {

    private IArticleService articleService;

    @Autowired
    public ArticleController(IArticleService articleService){
        this.articleService = articleService ;
    }
    @GetMapping("/listar")
    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ArticleDtoSalida>> listarArticulos(){
        return   new ResponseEntity<>(articleService.listarArticulos(), HttpStatus.OK);
    }

    @PostMapping("/crear")
    @PreAuthorize("hasAuthority('CREATE')")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ArticleDtoSalida> crearArticulo(@RequestBody ArticleDTO articulo) throws ResourceNotFoundException {
        return  new ResponseEntity<>(articleService.crearArticulo(articulo), HttpStatus.CREATED);
    }

    @GetMapping("/ultimos-cuatro")
    @PreAuthorize("permitAll()")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<ArticleAuthorSalidaDto> getLatestArticles() {
        return articleService.getLatestArticles();
    }


    @GetMapping(path = "/{id}")
    @PreAuthorize("permitAll()")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ArticleDtoSalida> listarArticulosPorId(@PathVariable Long id){
        try {
            ArticleDtoSalida articulo = articleService.buscarArticuloPorId(id);
            return new ResponseEntity<>(articulo, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<String> eliminarArticulo(@PathVariable Long id) {
        try {
            articleService.eliminarArticulo(id);
            return new ResponseEntity<>("Artículo eliminado con éxito", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Artículo no encontrado", HttpStatus.NOT_FOUND);
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('UPDATE')")
    public ResponseEntity<?> actualizarArticulo(@Valid @RequestBody ArticleModificacionEntradaDTO articleModificacionEntradaDTO, @PathVariable Long id) throws ResourceNotFoundException {
        // Llamar al servicio para actualizar el artículo
        ArticleDtoSalida articleDtoSalida = articleService.actualizarArticulo(articleModificacionEntradaDTO, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>("Artículo actualizado exitosamente", articleDtoSalida));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
