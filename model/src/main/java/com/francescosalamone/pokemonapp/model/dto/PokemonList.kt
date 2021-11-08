package com.francescosalamone.pokemonapp.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

data class PokemonList (
    val count: Int? = null,
    val next: String? = null,
    val previous: String? = null,
    val results: List<PokemonData> = listOf()
) {

    @Entity(tableName = "pokemon_list_table")
    data class PokemonData(
        @PrimaryKey(autoGenerate = true) val uid: Int,
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