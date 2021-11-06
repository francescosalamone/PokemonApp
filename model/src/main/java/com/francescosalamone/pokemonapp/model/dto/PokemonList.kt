package com.francescosalamone.pokemonapp.model.dto

data class PokemonList (
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokemonData> = listOf()
) {
    data class PokemonData(
        val name: String? = null,
        val url: String? = null
    ) {
        val id: String?
            get() = url?.removeSuffix("/")?.substringAfterLast("/")

        val pictureUrl: String?
            get() = id?.let {
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
            }
    }
}