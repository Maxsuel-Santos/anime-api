package com.github.maxsuel.anime_api.service;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.maxsuel.anime_api.controller.AnimeController;
import com.github.maxsuel.anime_api.domain.Anime;
import com.github.maxsuel.anime_api.repository.AnimeRepository;
import com.github.maxsuel.anime_api.requests.AnimePostRequestBody;
import com.github.maxsuel.anime_api.requests.AnimePutRequestBody;
import com.github.maxsuel.anime_api.util.AnimeCreator;
import com.github.maxsuel.anime_api.util.AnimePostRequestBodyCreator;
import com.github.maxsuel.anime_api.util.AnimePutRequestBodyCreator;
import com.github.maxsuel.anime_api.util.DateUtil;

@ExtendWith(MockitoExtension.class)
public class AnimeServiceTest {

    @InjectMocks
    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepositoryMock;

    @Mock
    private DateUtil dateUtilMock;

    @Test
    @DisplayName("listAll returns list of animes inside page object when successful")
    public void listAll_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        Page<Anime> animePageResponse = animeService.listAll(PageRequest.of(0, 5));

        Assertions.assertThat(animePageResponse).isNotNull();
        Assertions.assertThat(animePageResponse.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePageResponse.toList().get(0).getName())
                .isEqualTo(AnimeCreator.createValidAnime().getName());
    }

    @Test
    @DisplayName("listAllNonPageable returns list of animes when successful")
    public void listAllNonPageable_ReturnsListOfAnimes_WhenSuccessful() {
        List<Anime> expectedList = List.of(AnimeCreator.createValidAnime());

        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn(expectedList);

        List<Anime> animes = animeService.listAllNonPageable();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes.get(0).getName())
                .isEqualTo(AnimeCreator.createValidAnime().getName());
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns anime when successful")
    public void findByIdOrThrowBadRequestException_ReturnsAnimes_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        Anime anime = animeService.findByIdOrThrowBadRequestException(1L);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when anime is not found")
    public void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(Exception.class)
                .isThrownBy(() -> animeService.findByIdOrThrowBadRequestException(1L));
    }

    @Test
    @DisplayName("findByName returns a List of anime when successful")
    public void findByName_ReturnsListOfAnimes_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();

        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(new PageImpl<>(List.of(AnimeCreator.createValidAnime())));

        Page<Anime> animePage = animeService.findByName("anime", null);
        Anime anime = animePage.toList().get(0);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when no anime is found")
    public void findByName_ReturnsEmptyListOfAnimes_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(Page.empty());

        Page<Anime> animePage = animeService.findByName("anime", null);

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isEmpty();
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();

        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);

    }

    @Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful() {
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        Assertions.assertThatCode(() -> animeService.delete(1L))
                .doesNotThrowAnyException();

        BDDMockito.verify(animeRepositoryMock, Mockito.times(1)).delete(ArgumentMatchers.any(Anime.class));
    }

}
