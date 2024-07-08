package com.ia.inv_arg.DTO.entrada;

import com.ia.inv_arg.DTO.salida.AuthorDtoSalida;
import com.ia.inv_arg.DTO.salida.UserDtoSalida;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ArticleDTO {
    @NotNull(message = "El titulo no puede ser nulo")
    private String title;
    @NotNull(message = "El subtitulo no puede ser nulo")
    private String subtitle;
    @Size(max = 505535, message = "Paragraph is too long.")
    private String paragraph;

    private String image;

    private LocalDateTime publicDate;
    @NotNull(message = "El autor no puede ser nulo")
    @Valid
    private AuthorDtoSalida userAuthorDto;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(LocalDateTime publicDate) {
        this.publicDate = publicDate;
    }


}
