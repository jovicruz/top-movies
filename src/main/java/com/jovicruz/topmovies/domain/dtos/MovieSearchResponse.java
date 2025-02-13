package com.jovicruz.topmovies.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieSearchResponse {
    //Apenas os valores necessarios sobre o filme para a contrução da pagina de resultados
    private String id;
    private String title;
    private String posterpath;

}
