package com.ia.inv_arg.DTO.salida;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorDtoSalida {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
}
