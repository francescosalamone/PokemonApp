package com.francescosalamone.pokemonapp.data.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.UnsupportedEncodingException

@ProvidedTypeConverter
class PokemonConverter(
    private val moshi: Moshi
) {

    @TypeConverter
    fun toAbilityList(string: String): List<Pokemon.AbilityData> {
        val listMyData = Types.newParameterizedType(
            List::class.java,
            Pokemon.AbilityData::class.java
        )

        return moshi.adapter<List<Pokemon.AbilityData>>(listMyData).fromJson(string) ?: listOf()
    }

    @TypeConverter
    fun fromAbilityList(list: List<Pokemon.AbilityData>): String {
        val listMyData = Types.newParameterizedType(
            List::class.java,
            Pokemon.AbilityData::class.java
        )

        return moshi.adapter<List<Pokemon.AbilityData>>(listMyData).toJson(list)
    }

    @TypeConverter
    fun toTypeList(string: String): List<Pokemon.TypeWrapper> {
        val listMyData = Types.newParameterizedType(
            List::class.java,
            Pokemon.TypeWrapper::class.java
        )

        return moshi.adapter<List<Pokemon.TypeWrapper>>(listMyData).fromJson(string) ?: listOf()
    }

    @TypeConverter
    fun fromTypeList(list: List<Pokemon.TypeWrapper>): String {
        val listMyData = Types.newParameterizedType(
            List::class.java,
            Pokemon.TypeWrapper::class.java
        )

        return moshi.adapter<List<Pokemon.TypeWrapper>>(listMyData).toJson(list)
    }

    @TypeConverter
    fun toStatsList(string: String): List<Pokemon.StatWrapper> {
        val listMyData = Types.newParameterizedType(
            List::class.java,
            Pokemon.StatWrapper::class.java
        )

        return moshi.adapter<List<Pokemon.StatWrapper>>(listMyData).fromJson(string) ?: listOf()
    }

    @TypeConverter
    fun fromStatsList(list: List<Pokemon.StatWrapper>): String {
        val listMyData = Types.newParameterizedType(
            List::class.java,
            Pokemon.StatWrapper::class.java
        )

        return moshi.adapter<List<Pokemon.StatWrapper>>(listMyData).toJson(list)
    }

    @TypeConverter
    fun toSprites(string: String): Pokemon.Sprites {
        return moshi.adapter(Pokemon.Sprites::class.java).fromJson(string) ?: throw UnsupportedEncodingException()
    }

    @TypeConverter
    fun fromSprites(sprites: Pokemon.Sprites): String {
        return moshi.adapter(Pokemon.Sprites::class.java).toJson(sprites)
    }
}