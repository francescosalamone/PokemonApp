package com.francescosalamone.pokemonapp.data.repository

import com.francescosalamone.pokemonapp.data.dto.PokemonList
import com.francescosalamone.pokemonapp.data.source.RemoteDataSource
import retrofit2.Response

class PokemonRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): PokemonRepository {

    override suspend fun getPokemonList(limit: UInt, offset: UInt): Response<PokemonList> {
        return remoteDataSource.getPokemonList(limit, offset)
    }
}