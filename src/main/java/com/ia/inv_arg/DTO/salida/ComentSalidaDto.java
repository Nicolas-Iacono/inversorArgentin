package com.ia.inv_arg.DTO.salida;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ComentSalidaDto {
    private Long id;
    private String contenido;
    private Date publicDate;
    private Long articleId;
    private Long userId;
}
