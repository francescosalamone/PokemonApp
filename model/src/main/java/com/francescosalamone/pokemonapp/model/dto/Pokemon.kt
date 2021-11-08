package com.francescosalamone.pokemonapp.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "pokemon_table")
data class Pokemon (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val abilities: List<AbilityData> = emptyList(),
    @field:Json(name = "base_experience") val baseExperience: Int? = null,
    val name: String? = null,
    val sprites: Sprites? = null,
    val types: List<TypeWrapper>? = emptyList(),
    val stats: List<StatWrapper> = emptyList(),
    val weight: Int? = null,
    val height: Int? = null
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
                @field:Json(name = "normal") NORMAL(0xFFA8A878, 0xFF000000),
                @field:Json(name = "fighting") FIGHTING(0xFFC03028, 0XFFFFFFFF),
                @field:Json(name = "flying") FLYING(0xFFA890F0, 0xFF000000),
                @field:Json(name = "poison") POISON(0xFFA040A0, 0XFFFFFFFF),
                @field:Json(name = "ground") GROUND(0xFFE0C068, 0xFF000000),
                @field:Json(name = "rock") ROCK(0xFFB8A038, 0xFF000000),
                @field:Json(name = "bug") BUG(0xFFA8B820, 0xFF000000),
                @field:Json(name = "ghost") GHOST(0xFF705898, 0XFFFFFFFF),
                @field:Json(name = "steel") STEEL(0xFFB8B8D0, 0xFF000000),
                @field:Json(name = "fire") FIRE( 0xFFF08030, 0xFF000000),
                @field:Json(name = "water") WATER(0xFF6890F0, 0xFF000000),
                @field:Json(name = "grass") GRASS(0xFF78C850, 0xFF000000),
                @field:Json(name = "electric") ELECTRIC(0xFFF8D030, 0xFF000000),
                @field:Json(name = "psychic") PSYCHIC(0xFFF85888, 0xFF000000),
                @field:Json(name = "ice") ICE(0xFF98D8D8, 0xFF000000),
                @field:Json(name = "dragon") DRAGON(0xFF7038F8, 0XFFFFFFFF),
                @field:Json(name = "dark") DARK(0xFF705848, 0XFFFFFFFF),
                @field:Json(name = "fairy") FAIRY(0xFFEE99AC, 0xFF000000)
            }
        }
    }

    data class StatWrapper(
        @field:Json(name = "base_stat") val baseStat: Int? = null,
        val effort: Int? = null,
        val stat: StatDetail? = null
    ) {
        data class StatDetail(
            @field:Json(name = "name") val type: Stat? = null,
            val url: String? = null
        ) {
            enum class Stat(val color: Long) {
                @field:Json(name = "hp") HP(0xFFED398D),
                @field:Json(name = "attack") ATTACK(0xFFD42513),
                @field:Json(name = "defense") DEFENCE(0xFF5889D3),
                @field:Json(name = "special-attack") SPECIAL_ATTACK(0xFF992234),
                @field:Json(name = "special-defense") SPECIAL_DEFENCE(0xFF3F9899),
                @field:Json(name = "speed") SPEED(0xFF439936),
                @field:Json(name = "accuracy") ACCURACY(0xFFC7DB63),
                @field:Json(name = "evasion") EVASION(0xFFDBB8A6)
            }
        }
    }
}