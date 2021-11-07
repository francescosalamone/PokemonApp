package com.francescosalamone.pokemonapp.ui.layout

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun PokemonDetailLayout (
    name: String
) {
    Text(text = "Pokemon detail for $name")
}