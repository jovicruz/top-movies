package com.jovicruz.topmovies.domain.movie;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    List<Movie> findAllByOrderByDateaddedAsc();
    Movie getMovieById(String id);
}

