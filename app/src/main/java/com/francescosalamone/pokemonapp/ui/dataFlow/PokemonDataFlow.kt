package com.francescosalamone.pokemonapp.ui.dataFlow

import com.francescosalamone.pokemonapp.domain.usecase.FetchPokemonListUseCase
import com.francescosalamone.pokemonapp.model.state.DataState
import com.francescosalamone.pokemonapp.ui.contract.PokemonState
import io.uniflow.android.AndroidDataFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PokemonDataFlow(
    private val fetchAllPokemon: FetchPokemonListUseCase
) : AndroidDataFlow() {

    init {
        coroutineScope.launch {
            setState { PokemonState.Init }
        }
    }

    fun fetchPokemons() = action(
        onAction = {
            fetchAllPokemon.getPokemonList(20u, 0u)
                .collect {
                    setState {
                        when (it) {
                            is DataState.Loading -> PokemonState.Loading
                            is DataState.Success -> PokemonState.PokemonResult(it.data)
                            is DataState.Failure -> PokemonState.Failure(it.error)
                        }
                    }
                }
        },
        onError = { error, _ ->
            setState{ PokemonState.Failure(error) }
        })
}