package com.francescosalamone.model.dto

data class PokemonList (
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokemonData> = listOf()
) {
    data class PokemonData(
        val name: String? = null,
        val url: String? = null
    )
}