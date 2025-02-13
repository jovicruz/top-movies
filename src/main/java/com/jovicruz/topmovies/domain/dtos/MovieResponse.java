package com.jovicruz.topmovies.domain.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {
    //Apenas os valores necessarios sobre o filme e o total de paginas da pesquisa
    private List<MovieSearchResponse> filmes;
    private int totalPages;
}
