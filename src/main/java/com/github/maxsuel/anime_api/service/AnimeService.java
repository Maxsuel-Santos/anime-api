package com.github.maxsuel.anime_api.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.maxsuel.anime_api.domain.Anime;
import com.github.maxsuel.anime_api.exceptions.BadRequestException;
import com.github.maxsuel.anime_api.mapper.AnimeMapper;
import com.github.maxsuel.anime_api.repository.AnimeRepository;
import com.github.maxsuel.anime_api.requests.AnimePostRequestBody;
import com.github.maxsuel.anime_api.requests.AnimePutRequestBody;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimeService {
    
    private final AnimeRepository animeRepository;
    
    public Page<Anime> listAll(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }
    
    public List<Anime> listAllNonPageable() {
        return animeRepository.findAll();
    }

    public Page<Anime> findByName(String name, Pageable pageable) {
        return animeRepository.findByName(name, pageable);
    }

    public Anime findByIdOrThrowBadRequestException(Long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not found."));
    }

    @Transactional
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void delete(Long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());
        animeRepository.save(anime);
    }


}
