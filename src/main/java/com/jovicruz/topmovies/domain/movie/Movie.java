package com.jovicruz.topmovies.domain.movie;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "filme")
@Entity(name = "filme")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    private String id;

    private String title;
    private String posterpath;
    @Column(length = 1000)
    private String overview;
    private String genres;
    private String tagline;
    private String runtime;
    private String backdrop_path;
    private Integer year;
    private LocalDateTime dateadded;
}
