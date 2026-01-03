package com.github.maxsuel.anime_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.github.maxsuel.anime_api.domain.Anime;
import com.github.maxsuel.anime_api.requests.AnimePostRequestBody;
import com.github.maxsuel.anime_api.requests.AnimePutRequestBody;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {

    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);
    public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);

}
