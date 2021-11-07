package com.francescosalamone.pokemonapp.model.dto

import com.squareup.moshi.Json

data class Pokemon (
    val abilities: List<AbilityData> = emptyList(),
    @field:Json(name = "base_experience") val baseExperience: Int? = null,
    val name: String? = null,
    val sprites: Sprites? = null,
    val types: List<TypeWrapper>? = emptyList()
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

    data class TypeWrapper(
        val slot: Int? = null,
        val type: TypeDetail? = null
    ) {
        data class TypeDetail(
            val name: Type? = null,
            val url: String? = null
        ) {
            enum class Type(val color: Long, val textColor: Long) {
                @Json(name = "normal") NORMAL(0xFFA8A878, 0xFF000000),
                @Json(name = "fighting") FIGHTING(0xFFC03028, 0XFFFFFFFF),
                @Json(name = "flying") FLYING(0xFFA890F0, 0xFF000000),
                @Json(name = "poison") POISON(0xFFA040A0, 0XFFFFFFFF),
                @Json(name = "ground") GROUND(0xFFE0C068, 0xFF000000),
                @Json(name = "rock") ROCK(0xFFB8A038, 0xFF000000),
                @Json(name = "bug") BUG(0xFFA8B820, 0xFF000000),
                @Json(name = "ghost") GHOST(0xFF705898, 0XFFFFFFFF),
                @Json(name = "steel") STEEL(0xFFB8B8D0, 0xFF000000),
                @Json(name = "fire") FIRE( 0xFFF08030, 0xFF000000),
                @Json(name = "water") WATER(0xFF6890F0, 0xFF000000),
                @Json(name = "grass") GRASS(0xFF78C850, 0xFF000000),
                @Json(name = "electric") ELECTRIC(0xFFF8D030, 0xFF000000),
                @Json(name = "psychic") PSYCHIC(0xFFF85888, 0xFF000000),
                @Json(name = "ice") ICE(0xFF98D8D8, 0xFF000000),
                @Json(name = "dragon") DRAGON(0xFF7038F8, 0XFFFFFFFF),
                @Json(name = "dark") DARK(0xFF705848, 0XFFFFFFFF),
                @Json(name = "fairy") FAIRY(0xFFEE99AC, 0xFF000000)
            }
        }
    }
}