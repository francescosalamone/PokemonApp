package com.francescosalamone.pokemonapp.ui.dataFlow

import com.francescosalamone.pokemonapp.data.repository.PokemonRepository
import com.francescosalamone.pokemonapp.ui.contract.PokemonState
import io.uniflow.android.AndroidDataFlow
import kotlinx.coroutines.launch

class PokemonDataFlow(
    private val repository: PokemonRepository
) : AndroidDataFlow() {

    init {
        coroutineScope.launch {
            setState { PokemonState.Init }
        }
    }

    fun fetchPokemons() = action(
        onAction = {
            setState { PokemonState.Loading }
            val result = repository.getPokemonList(50u, 0u)
            if (result.isSuccessful) {
                setState {
                    result.body()?.let { PokemonState.PokemonResult(it) }
                        ?: PokemonState.Failure(Exception("Missing body content."))
                }
            } else {
                setState { PokemonState.Failure(Exception(result.errorBody()?.string())) }
            }
        },
        onError = { error, _ ->
            setState{ PokemonState.Failure(error) }
        })
}