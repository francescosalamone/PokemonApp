package com.francescosalamone.pokemonapp.model.dto

import com.squareup.moshi.Json

data class Pokemon (
    val abilities: List<AbilityData> = listOf(),
    @field:Json(name = "base_experience") val baseExperience: Int? = null,
    val name: String? = null,
    val sprites: Sprites? = null
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

    data class Sprites(
        @field:Json(name = "back_default") val backDefault: String? = null,
        @field:Json(name = "back_female") val backFemale: String? = null,
        @field:Json(name = "back_shiny") val backShiny: String? = null,
        @field:Json(name = "back_shiny_female") val backShinyFemale: String? = null,
        @field:Json(name = "front_default") val frontDefault: String? = null,
        @field:Json(name = "front_female") val frontFemale: String? = null,
        @field:Json(name = "front_shiny") val frontShiny: String? = null,
        @field:Json(name = "front_shiny_female") val frontShinyFemale: String? = null,
        val other: Other? = null
    ) {
        data class Other(
            @field:Json(name = "official-artwork") val officialArtwork: OfficialArtWork? = null
        ) {
            data class OfficialArtWork(
                @field:Json(name = "front_default") val frontDefault: String? = null
            )
        }
    }
}