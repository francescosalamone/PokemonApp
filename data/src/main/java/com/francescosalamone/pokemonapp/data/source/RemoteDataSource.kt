package com.francescosalamone.pokemonapp.data.source

import com.francescosalamone.pokemonapp.data.api.PokemonService
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import retrofit2.Response
import timber.log.Timber

class RemoteDataSource(private val service: PokemonService): DataSource {

    override suspend fun getPokemonList(limit: Int, offset: Int): PokemonList {
        return service.getPokemonList(limit, offset).getOrThrow()
    }

    override suspend fun getPokemon(name: String): Pokemon {
        return service.getPokemon(name).getOrThrow()
    }

    private fun<T> Response<T>.getOrThrow(): T {
        if(isSuccessful) {
            body()?.let {
                return it
            } ?: run {
                Timber.e("RESPONSE NETWORK: Missing body content.")
                throw(Exception("Missing body content."))
            }
        } else {
            val error = errorBody()?.string()
            Timber.e("RESPONSE NETWORK: $error")
            throw(Exception(error))
        }
    }
}