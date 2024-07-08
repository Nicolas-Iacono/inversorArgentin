package com.ia.inv_arg.DTO.modificacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ia.inv_arg.DTO.entrada.ArticleDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModificacionEntradaDTO {

    private Long id;

    private String username;
    @NotNull(message = "El nombre del paciente no puede ser nulo")
    @NotBlank(message = "Debe especificarse el nombre del paciente")
    @Size(max = 50, message = "El nombre debe tener hasta 50 caracteres")
    private String firstname;

    @Size(max = 50, message = "El apellido debe tener hasta 50 caracteres")
    @NotBlank(message = "Debe especificarse el apellido del paciente")
    private String lastname;
    @Email(message = "el email debe ser valido")
    private String email;

    @NotBlank(message = "Debe tener una contraseña")
    private String password;

    private List<ArticleModificacionEntradaDTO> articlesModificaionEntradaDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ArticleModificacionEntradaDTO> getArticlesModificaionEntradaDto() {
        return articlesModificaionEntradaDto;
    }

    public void setArticlesModificaionEntradaDto(List<ArticleModificacionEntradaDTO> articlesModificaionEntradaDto) {
        this.articlesModificaionEntradaDto = articlesModificaionEntradaDto;
    }
}
