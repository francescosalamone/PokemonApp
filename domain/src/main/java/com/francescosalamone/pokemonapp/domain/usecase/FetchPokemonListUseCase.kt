package com.francescosalamone.pokemonapp.domain.usecase

import com.francescosalamone.pokemonapp.data.repository.PokemonRepository
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import com.francescosalamone.pokemonapp.model.state.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class FetchPokemonListUseCase(
    private val repository: PokemonRepository
) {

    fun getPokemonList(
        limit: UInt,
        offset: UInt
    ): Flow<DataState<PokemonList>> {
        return repository.getPokemonList(limit, offset)
            .flowOn(Dispatchers.IO)
    }

}