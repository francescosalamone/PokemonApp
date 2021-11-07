package com.francescosalamone.pokemonapp.ui.dataFlow

import com.francescosalamone.pokemonapp.domain.usecase.FetchPokemonListUseCase
import com.francescosalamone.pokemonapp.domain.usecase.PokemonDetailUseCase
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import com.francescosalamone.pokemonapp.model.state.DataState
import com.francescosalamone.pokemonapp.ui.contract.PokemonState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PokemonDataFlow(
    private val fetchAllPokemon: FetchPokemonListUseCase,
    private val pokemonDetailUseCase: PokemonDetailUseCase
): PokemonVm() {

    override var pokemons: MutableList<PokemonList.PokemonData> = mutableListOf()

    override var scrollPosition: Int = 0

    init {
        coroutineScope.launch {
            setState { PokemonState.Init }
        }
    }

    override fun fetchPokemons() = action(
        onAction = {
            fetchAllPokemon.getPokemonList()
                .collect {
                    setState {
                        when (it) {
                            is DataState.Loading -> PokemonState.Loading(pokemons)
                            is DataState.Success -> {
                                pokemons.addAll(it.data.results)
                                PokemonState.PokemonResult(pokemons)
                            }
                            is DataState.Failure -> PokemonState.Failure(it.error)
                        }
                    }
                }
        },
        onError = { error, _ ->
            setState{ PokemonState.Failure(error) }
        })

    override fun getPokemonDetail(name: String) = action(
        onAction = {
            pokemonDetailUseCase.getPokemonDetail(name)
                .collect {
                    setState {
                        when (it) {
                            is DataState.Loading -> PokemonState.Loading(pokemons)
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