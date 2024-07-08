package com.ia.inv_arg.DTO.entrada;

import com.ia.inv_arg.entity.Article;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {


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

    private List<ArticleDTO> articlesDto;

    public List<ArticleDTO> getArticlesDto() {
        return articlesDto;
    }

    public void setArticlesDto(List<ArticleDTO> articlesDto) {
        this.articlesDto = articlesDto;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
