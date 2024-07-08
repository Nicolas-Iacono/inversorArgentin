package com.ia.inv_arg.DTO.modificacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ia.inv_arg.DTO.entrada.UserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleModificacionEntradaDTO {

    private Long id;
    @NotNull(message = "El titulo no puede ser nulo")
    private String title;
    @NotNull(message = "El subtitulo no puede ser nulo")
    private String subtitle;

    private String paragraph;

    private String image;

    private OffsetDateTime publicDate;

    private UserModificacionEntradaDTO userModificacionEntradaDTOAuthor;
}
