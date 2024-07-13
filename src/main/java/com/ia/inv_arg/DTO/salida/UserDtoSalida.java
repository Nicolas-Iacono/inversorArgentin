package com.ia.inv_arg.DTO.salida;

import com.ia.inv_arg.entity.Article;
import com.ia.inv_arg.entity.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDtoSalida {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private Set<ArticleAuthorSalidaDto> articlesSalida;
    private Set<Role> roles;



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


}
