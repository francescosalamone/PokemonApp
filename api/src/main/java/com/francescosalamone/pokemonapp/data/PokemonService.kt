package com.francescosalamone.pokemonapp.data

import com.francescosalamone.pokemonapp.data.model.PokemonList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: UInt,
        @Query("offset") offset: UInt
    ): Response<PokemonList>


}