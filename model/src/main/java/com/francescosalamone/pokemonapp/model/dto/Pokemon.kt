package com.francescosalamone.pokemonapp.model.dto

import com.squareup.moshi.Json

data class Pokemon (
    val abilities: List<AbilityData> = listOf(),
    @field:Json(name = "base_experience") val baseExperience: Int? = null,
    val name: String? = null
) {
    data class AbilityData(
        val ability: Ability? = null,
        @field:Json(name = "is_hidden") val isHidden: Boolean? = null,
        val slot: Int? = null
    ) {
        data class Ability(
            val name: String? = null,
            val url: String? = null
        )
    }
}