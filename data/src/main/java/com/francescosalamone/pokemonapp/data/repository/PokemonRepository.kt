package com.francescosalamone.pokemonapp.data.repository

import com.francescosalamone.pokemonapp.data.dto.PokemonList
import retrofit2.Response

interface PokemonRepository {

    suspend fun getPokemonList(
        limit: UInt,
        offset: UInt
    ): Response<PokemonList>
}