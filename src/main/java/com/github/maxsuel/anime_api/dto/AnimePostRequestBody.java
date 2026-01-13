package com.github.maxsuel.anime_api.dto;

import jakarta.validation.constraints.NotBlank;

public record AnimePostRequestBody(
        @NotBlank String name
) {}
