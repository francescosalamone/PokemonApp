package com.francescosalamone.pokemonapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.model.dto.PokemonList

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_list_table LIMIT :limit OFFSET :offset")
    fun getPokemonList(
        limit: Int,
        offset: Int
    ): List<PokemonList.PokemonData>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemonList(pokemonData: List<PokemonList.PokemonData>)

    @Query("SELECT * FROM pokemon_table WHERE name=:name")
    suspend fun getPokemon(
        name: String
    ): Pokemon?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemon(pokemon: Pokemon)
}