package com.francescosalamone.pokemonapp.ui.contract

import com.francescosalamone.pokemonapp.model.dto.PokemonList
import io.uniflow.core.flow.data.UIState

sealed class PokemonState <T>: UIState() {
    object Init: PokemonState<Nothing>()
    data class Loading(val pokemons: List<PokemonList.PokemonData>): PokemonState<Nothing>()
    data class PokemonResult<T>(val data: T): PokemonState<T>()
    data class Failure(val exception: Exception): PokemonState<Nothing>()
}