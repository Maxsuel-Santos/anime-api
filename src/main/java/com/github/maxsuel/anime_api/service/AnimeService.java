package com.github.maxsuel.anime_api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.maxsuel.anime_api.domain.Anime;

@Service
public class AnimeService {

    private List<Anime> animes = List.of(new Anime(1L, "Berserk"), new Anime(2L, "One Piece"));

    public List<Anime> listAll() {
        return animes;
    }

    public Anime findById(Long id) {
        return animes.stream()
                     .filter(anime -> anime.getId().equals(id))
                     .findFirst()
                     .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found."));
    }

}
