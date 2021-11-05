package com.francescosalamone.pokemonapp.ui.contract

import com.francescosalamone.pokemonapp.data.dto.PokemonList
import io.uniflow.core.flow.data.UIState

sealed class PokemonState: UIState() {
    object Init: PokemonState()
    data class PokemonResult(val pokemons: PokemonList): PokemonState()
    data class Failure(val exception: Exception): PokemonState()
}