package com.example.rickmorty_api;

import com.example.rickmorty_api.character.Episode;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RickAndMortyApi {
    @GET("character")
    Call<ApiResponse> getCharacters();

    @GET
    Call<Episode> getEpisodeDetails(@Url String episodeUrl);
}


