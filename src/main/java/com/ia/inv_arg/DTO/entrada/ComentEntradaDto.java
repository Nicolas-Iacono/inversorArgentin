package com.ia.inv_arg.DTO.entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComentEntradaDto {

    private String contenido;

    @NotNull(message = "El ID del artículo no puede ser nulo")
    private Long article;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Long user;
}
