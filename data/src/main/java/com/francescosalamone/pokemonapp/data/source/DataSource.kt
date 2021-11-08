package com.francescosalamone.pokemonapp.data.source

import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.model.dto.PokemonList

interface DataSource {

    suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ): PokemonList

    suspend fun getPokemon(
        name: String
    ): Pokemon
}