package com.ia.inv_arg.service;

import com.ia.inv_arg.DTO.entrada.ArticleDTO;
import com.ia.inv_arg.DTO.entrada.ComentEntradaDto;
import com.ia.inv_arg.DTO.salida.ArticleDtoSalida;
import com.ia.inv_arg.DTO.salida.ComentSalidaDto;
import com.ia.inv_arg.DTO.salida.UserDtoSalida;
import com.ia.inv_arg.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IComentService {

    ComentSalidaDto crearComentario(ComentEntradaDto comentEntradaDto) throws ResourceNotFoundException;

    List<ComentSalidaDto> listarComentarios(Long id);
}
