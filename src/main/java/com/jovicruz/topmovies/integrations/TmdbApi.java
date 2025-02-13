package com.jovicruz.topmovies.integrations;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jovicruz.topmovies.domain.dtos.MovieResponse;
import com.jovicruz.topmovies.domain.dtos.MovieSearchResponse;
import com.jovicruz.topmovies.domain.movie.Movie;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class TmdbApi {
    Dotenv dotenv = Dotenv.load();
    String TMDB_API_TOKEN = dotenv.get("API_TOKEN");
    //String TMDB_API_TOKEN = System.getenv("API_TOKEN");

    public TmdbApi() {
        // Verificar se o token está definido
        if (TMDB_API_TOKEN == null || TMDB_API_TOKEN.isEmpty()) {
            throw new IllegalStateException("Variável de ambiente TMDB_API_TOKEN não definida! Siga as instruções do README corretamente para definir seu token!");
        }
    }

    // Pesquisa o filme pelo seu id e retorna as informações dele
    public Movie requestMovieById(String id) throws IOException {
        Movie filme = new Movie();
        OkHttpClient client = new OkHttpClient();
        String genres = "";
        Request request = new Request.Builder()
            .url("https://api.themoviedb.org/3/movie/" + id + "?language=pt-BR")
            .get()
            .addHeader("Authorization", "Bearer " + TMDB_API_TOKEN)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Falha na requisição: " + response);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode result = objectMapper.readTree(response.body().string());

            JsonNode genresResults = result.get("genres");
            if (genresResults.isArray()) {
                for (JsonNode genre : genresResults) {
                    if (genres.isEmpty()) {
                        genres = genre.get("name").asText();
                    } else {
                        genres += ", " + genre.get("name").asText();
                    }
                }
            }

            String title = result.get("title").asText();
            String posterPath = result.get("poster_path").asText();
            String overview = result.get("overview").asText();
            String tagline = result.get("tagline").asText();
            String runtime = result.get("runtime").asText();
            String backdropPath = result.get("backdrop_path").asText();
            LocalDate releaseDate = LocalDate.parse(result.get("release_date").asText());

            filme.setId(id);
            filme.setTitle(title);
            filme.setPosterpath(posterPath);
            filme.setOverview(overview);
            filme.setGenres(genres);
            filme.setTagline(tagline);
            filme.setRuntime(runtime + " mins");
            filme.setBackdrop_path(backdropPath);
            filme.setYear(releaseDate.getYear());

        } catch (IOException e) {
            System.err.println("Erro ao buscar filme por ID: " + e.getMessage());
            throw e;  // Re-throw the exception after logging
        }

        return filme;
    }

    public MovieResponse requestMovie(String query, int page) throws IOException {
        List<MovieSearchResponse> filmes = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        int totalPages = 0;
        Request request = new Request.Builder()
            .url("https://api.themoviedb.org/3/search/movie?query=" + query + "&include_adult=false&language=pt-BR&page=" + page)
            .get()
            .addHeader("Authorization", "Bearer " + TMDB_API_TOKEN)  // Corrigido para adicionar "Bearer"
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Falha na requisição: " + response);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            JsonNode resultsNode = jsonNode.get("results");
            totalPages = jsonNode.get("total_pages").asInt();

            if (resultsNode.isArray()) {
                for (JsonNode result : resultsNode) {
                    MovieSearchResponse filme = new MovieSearchResponse();
                    String title = result.get("title").asText();
                    String posterPath = result.get("poster_path").asText();

                    if (!"null".equals(posterPath)) {
                        String id = result.get("id").asText();
                        filme.setId(id);
                        filme.setPosterpath(posterPath);
                        filme.setTitle(title);
                        filmes.add(filme);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao buscar filmes por query: " + e.getMessage());
            throw e;  // Re-throw the exception after logging
        }
        
        return new MovieResponse(filmes, totalPages);
    }

    public List<String> getMoviePostersById(String id) throws IOException {
        System.out.println("ENTROU API");
        List<String> listaPosters = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://api.themoviedb.org/3/movie/" + id + "/images")
            .get()
            .addHeader("Authorization", "Bearer " + TMDB_API_TOKEN)  // Corrigido para adicionar "Bearer"
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Falha na requisição: " + response);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode result = objectMapper.readTree(response.body().string());
            JsonNode postersResults = result.get("posters");

            if (postersResults.isArray()) {
                for (JsonNode poster : postersResults) {
                    listaPosters.add(poster.get("file_path").asText());
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao buscar posters do filme por ID: " + e.getMessage());
            throw e;  // Re-throw the exception after logging
        }

        return listaPosters;
    }
}
