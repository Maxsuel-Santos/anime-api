package com.github.maxsuel.anime_api.repository;

import com.github.maxsuel.anime_api.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional 
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when Successful")
    void save_PersistAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates anime when Successful")
    void save_UpdatesAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        animeSaved.setName("Overlord");
        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes anime when Successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Name returns list of anime in page object when Successful")
    void findByName_ReturnsListOfAnimeInPageObject_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        this.animeRepository.save(animeToBeSaved);

        String name = animeToBeSaved.getName();
        Page<Anime> animePage = this.animeRepository.findByName(name, PageRequest.of(0, 5));

        Assertions.assertThat(animePage).isNotEmpty();
        Assertions.assertThat(animePage.toList()).contains(animeToBeSaved);
    }

    private Anime createAnime() {
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }
}
