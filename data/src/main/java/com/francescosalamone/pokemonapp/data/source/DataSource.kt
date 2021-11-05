package com.francescosalamone.pokemonapp.data.source

import com.francescosalamone.model.dto.PokemonList
import retrofit2.Response

interface DataSource {

    suspend fun getPokemonList(
        limit: UInt,
        offset: UInt
    ): Response<PokemonList>
}