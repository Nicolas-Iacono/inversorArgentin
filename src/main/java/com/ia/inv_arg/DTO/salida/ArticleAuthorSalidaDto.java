package com.ia.inv_arg.DTO.salida;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ArticleAuthorSalidaDto {

    private Long id;
    private String title;
    private String subtitle;
    private String paragraph;
    private String image;
    private Date publicDate;
    private String emailAuthor;

}
