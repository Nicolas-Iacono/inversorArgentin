package com.ia.inv_arg.DTO.salida;

import com.ia.inv_arg.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenSalidaDto {
    private Long idUser;
    private String username;
    private String firstname;
    private String lastname;
    private List<Role> roles;
    private String token;
    @Builder.Default
    private String tokenType = "Bearer ";

    public TokenSalidaDto(Long idUser, String username, String firstname, String lastname, List<Role> roles, String token){
        this.idUser = idUser;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.roles = roles;
        this.token = token;
        this.tokenType = "Bearer ";
    }
}
