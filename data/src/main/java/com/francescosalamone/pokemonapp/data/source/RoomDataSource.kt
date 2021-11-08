package com.francescosalamone.pokemonapp.data.source

import com.francescosalamone.pokemonapp.data.dao.PokemonDao
import com.francescosalamone.pokemonapp.data.exception.MissingCachedDataException
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import timber.log.Timber

class RoomDataSource(private val dao: PokemonDao): DataSource {
    override suspend fun getPokemonList(limit: Int, offset: Int): PokemonList {
        val result = dao.getPokemonList(limit, offset)
        if(result?.isNullOrEmpty() == false) {
            return PokemonList(results = result)
        } else {
            throw MissingCachedDataException("Missing data from db")
        }

    }

    override suspend fun getPokemon(name: String): Pokemon {
        return dao.getPokemon(name) ?: throw MissingCachedDataException("Missing data from db")
    }

    suspend fun savePokemonList(pokemonList: PokemonList) {
        Timber.d("Saving pokemon list to DB")
        dao.insertPokemonList(pokemonList.results)
    }

    suspend fun savePokemon(pokemon: Pokemon) {
        Timber.d("Saving pokemon ${pokemon.name} to DB")
        dao.insertPokemon(pokemon)
    }
}