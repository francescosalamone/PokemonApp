package com.francescosalamone.pokemonapp.ui.contract

import com.francescosalamone.pokemonapp.model.dto.PokemonList
import io.uniflow.core.flow.data.UIState

sealed class PokemonState: UIState() {
    object Init: PokemonState()
    data class Loading(val pokemons: List<PokemonList.PokemonData>): PokemonState()
    data class PokemonResult(val pokemons: List<PokemonList.PokemonData>): PokemonState()
    data class Failure(val exception: Exception): PokemonState()
}