package com.francescosalamone.pokemonapp.data.repository

import com.francescosalamone.model.dto.PokemonList
import com.francescosalamone.model.state.DataState
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(
        limit: UInt,
        offset: UInt
    ): Flow<DataState<PokemonList>>
}