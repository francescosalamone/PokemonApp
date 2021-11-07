package com.francescosalamone.pokemonapp.ui.dataFlow

import com.francescosalamone.pokemonapp.model.dto.PokemonList
import io.uniflow.android.AndroidDataFlow

abstract class PokemonVm: AndroidDataFlow() {
    abstract var pokemons: MutableList<PokemonList.PokemonData>
    abstract var scrollPosition: Int

    abstract fun fetchPokemons()
    abstract fun getPokemonDetail(name: String)
}