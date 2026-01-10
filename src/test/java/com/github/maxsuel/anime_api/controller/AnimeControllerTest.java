package com.github.maxsuel.anime_api.controller;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.maxsuel.anime_api.domain.Anime;
import com.github.maxsuel.anime_api.requests.AnimePostRequestBody;
import com.github.maxsuel.anime_api.service.AnimeService;
import com.github.maxsuel.anime_api.util.AnimeCreator;
import com.github.maxsuel.anime_api.util.AnimePostRequestBodyCreator;
import com.github.maxsuel.anime_api.util.DateUtil;

@ExtendWith(MockitoExtension.class)
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;

    @Mock
    private AnimeService animeServiceMock;

    @Mock
    private DateUtil dateUtilMock;

    @Test
    @DisplayName("List returns list of animes inside page object when successful")
    public void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        Page<Anime> animePageResponse = animeController.list(null).getBody();

        Assertions.assertThat(animePageResponse).isNotNull();
        Assertions.assertThat(animePageResponse.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePageResponse.toList().get(0).getName())
                .isEqualTo(AnimeCreator.createValidAnime().getName());
    }

    @Test
    @DisplayName("listAll returns list of animes when successful")
    public void listAll_ReturnsListOfAnimes_WhenSuccessful() {
        List<Anime> expectedList = List.of(AnimeCreator.createValidAnime());

        BDDMockito.when(animeServiceMock.listAllNonPageable())
                .thenReturn(expectedList);

        List<Anime> animes = animeController.listAll().getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes.get(0).getName())
                .isEqualTo(AnimeCreator.createValidAnime().getName());
    }

    @Test
    @DisplayName("findByID returns anime when successful")
    public void findById_ReturnsAnimes_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();

        BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        Anime anime = animeController.findById(1L).getBody();

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns a List of anime when successful")
    public void findByName_ReturnsListOfAnimes_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(List.of(AnimeCreator.createValidAnime())));

        Page<Anime> animePage = animeController.findByName("anime", null).getBody();
        Anime anime = animePage.toList().get(0);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when no anime is found")
    public void findByName_ReturnsEmptyListOfAnimes_WhenAnimeIsNotFound() {
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(Page.empty());

        Page<Anime> animePage = animeController.findByName("anime", null).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isEmpty();
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
        
    }

}
