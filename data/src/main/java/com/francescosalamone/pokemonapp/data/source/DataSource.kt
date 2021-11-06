package com.francescosalamone.pokemonapp.data.source

import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import retrofit2.Response

interface DataSource {

    suspend fun getPokemonList(
        limit: UInt,
        offset: UInt
    ): Response<PokemonList>

    suspend fun getPokemon(
        name: String
    ): Response<Pokemon>
}