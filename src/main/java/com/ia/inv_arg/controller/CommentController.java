package com.ia.inv_arg.controller;

import com.ia.inv_arg.DTO.entrada.ComentEntradaDto;
import com.ia.inv_arg.DTO.salida.ComentSalidaDto;
import com.ia.inv_arg.exception.ResourceNotFoundException;
import com.ia.inv_arg.service.IComentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@PreAuthorize("denyAll()")
public class CommentController {
    @Autowired
    private IComentService comentService;

    @PostMapping
    @PreAuthorize("hasAuthority('COMENTAR')")
    public ResponseEntity<ComentSalidaDto> crearComentario(@RequestBody @Valid ComentEntradaDto comentEntradaDto)throws ResourceNotFoundException {
        ComentSalidaDto createComment = comentService.crearComentario(comentEntradaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createComment);
    }

    @GetMapping("/article/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ComentSalidaDto>> listarComentariosPorArticulo(@PathVariable Long id){
        List<ComentSalidaDto> comments = comentService.listarComentarios(id);
        return ResponseEntity.ok(comments);
    }
}
