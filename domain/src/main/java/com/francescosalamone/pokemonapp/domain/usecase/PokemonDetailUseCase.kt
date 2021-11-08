package com.francescosalamone.pokemonapp.domain.usecase

import com.francescosalamone.pokemonapp.data.repository.PokemonRepository
import com.francescosalamone.pokemonapp.model.base.UseCase
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.model.state.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class PokemonDetailUseCase(
    private val repository: PokemonRepository
): UseCase<PokemonDetailUseCase.Param, Pokemon> {

    data class Param(
        val name: String
    )

    override fun invoke(params: Param): Flow<DataState<Pokemon>> {
        return repository.getPokemon(params.name)
            .flowOn(Dispatchers.IO)
    }
}