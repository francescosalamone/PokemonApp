package com.francescosalamone.pokemonapp.data.source

import com.francescosalamone.model.dto.PokemonList
import com.francescosalamone.pokemonapp.data.api.PokemonService
import retrofit2.Response

class RemoteDataSource(private val service: PokemonService): DataSource {

    override suspend fun getPokemonList(limit: UInt, offset: UInt): Response<PokemonList> {
        return service.getPokemonList(limit, offset)
    }
}