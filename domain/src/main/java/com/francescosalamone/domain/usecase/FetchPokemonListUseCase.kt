package com.francescosalamone.domain.usecase

import com.francescosalamone.model.dto.PokemonList
import com.francescosalamone.model.state.DataState
import com.francescosalamone.pokemonapp.data.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class FetchPokemonListUseCase(
    private val repository: PokemonRepository
) {

    fun getPokemonList(
        limit: UInt,
        offset: UInt
    ): Flow<DataState<PokemonList>> {
        return repository.getPokemonList(limit, offset)
    }

}