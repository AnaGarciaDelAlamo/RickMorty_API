package com.example.rickmorty_api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RickAndMortyApi {
    @GET("character")
    Call<ApiResponse> getCharacters();
}


