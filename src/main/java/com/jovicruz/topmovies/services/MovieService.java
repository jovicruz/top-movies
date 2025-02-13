package com.jovicruz.topmovies.services;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jovicruz.topmovies.domain.dtos.MovieResponse;
import com.jovicruz.topmovies.domain.movie.Movie;
import com.jovicruz.topmovies.integrations.TmdbApi;
import com.jovicruz.topmovies.domain.movie.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;
    
    @Autowired
    private TmdbApi apiClient;

    public MovieResponse searchMovies(String query, int page) throws IOException {
        return apiClient.requestMovie(query, page);
    }

    public Boolean existsById(String id) throws IOException{
        return repository.existsById(id);
    }

    public Movie getMovieByIdRepo(String id) throws IOException{
        return repository.getMovieById(id);
    }

    public Movie getMovieById(String id) throws IOException {
        return apiClient.requestMovieById(id);
    }

    public List<String> getMoviePostersById(String Id) throws IOException{
        return apiClient.getMoviePostersById(Id);
    }

    public void toggleFavoriteMovie(Movie filme) throws IOException {
        String id = filme.getId();
        System.out.println("ID" + id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            String title = filme.getTitle();
            String posterpath = filme.getPosterpath();
            String overview = filme.getOverview();
            String genres = filme.getGenres();
            String tagline = filme.getTagline();
            String runtime = filme.getRuntime();
            String backdrop_path = filme.getBackdrop_path();
            int year = filme.getYear();

            
            System.out.println("TItle" + title);
            System.out.println("poster" + posterpath);
            System.out.println("over" + overview);
            System.out.println("genres" + genres);
            System.out.println("tagline" + tagline);
            System.out.println("runtime" + runtime);
            Movie movie = new Movie();
            movie.setId(id);
            movie.setTitle(title);
            movie.setPosterpath(posterpath);
            movie.setOverview(overview);
            movie.setGenres(genres);
            movie.setTagline(tagline);
            movie.setRuntime(runtime);
            movie.setBackdrop_path(backdrop_path);
            movie.setYear(year);
            movie.setDateadded(LocalDateTime.now());
            repository.save(movie);
        }
    }

    
    public Iterable<Movie> getAllFavorites() {
        return repository.findAllByOrderByDateaddedAsc();
    }

    public void updateMoviePoster(String id, String posterPath) throws IOException{
        if(repository.existsById(id)){
            Movie movie = repository.getMovieById(id);
            System.out.println(movie.getTitle());
            movie.setPosterpath(posterPath);
            repository.save(movie);
        }
        else{
            System.out.println("ERRO FILME NAO FAVORITO");
        }
    }

    public boolean isFavorited(String id) {
        return repository.existsById(id);
    }
}
