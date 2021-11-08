package com.francescosalamone.pokemonapp.data.repository

import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import com.francescosalamone.pokemonapp.model.state.DataState
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(
        limit: Int,
        offset: Int
    ): Flow<DataState<PokemonList>>

    fun getPokemon(name: String): Flow<DataState<Pokemon>>
}