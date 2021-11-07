package com.francescosalamone.pokemonapp.domain.usecase

import com.francescosalamone.pokemonapp.data.repository.PokemonRepository
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.model.state.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class PokemonDetailUseCase(
    private val repository: PokemonRepository
) {
    fun getPokemonDetail(name: String): Flow<DataState<Pokemon>> {
        return repository.getPokemon(name)
            .flowOn(Dispatchers.IO)
    }
}