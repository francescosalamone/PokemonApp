package com.francescosalamone.pokemonapp.domain.usecase

import com.francescosalamone.pokemonapp.data.repository.PokemonRepository
import com.francescosalamone.pokemonapp.model.base.UseCase
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import com.francescosalamone.pokemonapp.model.state.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class FetchPokemonListUseCase(
    private val repository: PokemonRepository,
    private val initialPage: Int,
    private val limit: Int
): UseCase<Unit, PokemonList> {
    private var currentPage = initialPage

    override fun invoke(params: Unit): Flow<DataState<PokemonList>> {
        val offset = currentPage * limit

        return repository.getPokemonList(limit, offset)
            .flowOn(Dispatchers.IO)
            .onEach {
                if(it is DataState.Success) {
                    currentPage++
                }
            }
    }

}