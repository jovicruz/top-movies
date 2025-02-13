package com.jovicruz.topmovies.controllers;

import java.io.IOException;

import com.jovicruz.topmovies.domain.movie.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import com.jovicruz.topmovies.domain.dtos.MovieResponse;
import com.jovicruz.topmovies.services.MovieService;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MoviesController {
    
    private MovieResponse response;
    @Autowired
    private MovieService service;

    @GetMapping("/")
    public String home(Model model){    
        model.addAttribute("page", "home");
        return "home";
    }

    @GetMapping("/search")
    public String pesquisar(Model model){    
        model.addAttribute("page", "search");
        return "search";
    }

    //envia a pesquisa para a API do TMDB e coloca no template 'results'
    @GetMapping("/q")
    public String listaFilmes(Model model, @RequestParam("query") String query, int page) throws IOException{    
        response = service.searchMovies(query, page);
        model.addAttribute("filmes", response.getFilmes());
        model.addAttribute("pesquisa", query);
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("prevPage", page - 1);
        model.addAttribute("totalPages", response.getTotalPages());
        model.addAttribute("page", "search");

        return "results";
    }

    //recebe o ID do filme escolhido e mostra as informações dele no template 'filme'
    @GetMapping("/filme")
    public String filme(Model model, @RequestParam("id") String id) throws IOException {   
        if (service.existsById(id)) {
            model.addAttribute("filme", service.getMovieByIdRepo(id));
        }
        else{
            model.addAttribute("filme", service.getMovieById(id));
        }
        model.addAttribute("id", id);
        model.addAttribute("favoritado", service.isFavorited(id));
        return "movie";
    }

    @GetMapping("/editPoster")
    public String editPoster(Model model, @RequestParam("id") String id) throws IOException {   
        model.addAttribute("posters", service.getMoviePostersById(id));
        model.addAttribute("id", id);

        return "posters";
    }

    @GetMapping("/favoritos")
    public String favoritos(Model model) throws IOException{ 
        model.addAttribute("filmes", service.getAllFavorites());  
        model.addAttribute("page", "favoritos"); 
        return "favorites";
    }

    @PostMapping("/editPoster")
    public String editPosterRedirect(Model model, @RequestParam("id") String id, 
    @RequestParam("posterpath") String posterPath) throws IOException {   
        service.updateMoviePoster(id, posterPath);

        return "redirect:/filme?id=" + id;
    }

    @PostMapping("/filme")
    public String favoritarFilme(Model model, @ModelAttribute Movie filme) throws IOException {
        System.out.println(filme);
        service.toggleFavoriteMovie(filme);

        return filme(model, filme.getId());
    }
}
