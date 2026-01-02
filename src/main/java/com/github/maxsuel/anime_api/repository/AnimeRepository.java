package com.github.maxsuel.anime_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.maxsuel.anime_api.domain.Anime;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
}
