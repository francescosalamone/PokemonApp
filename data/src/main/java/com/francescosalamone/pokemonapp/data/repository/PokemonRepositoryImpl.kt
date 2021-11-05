package com.francescosalamone.pokemonapp.data.repository

import com.francescosalamone.model.dto.PokemonList
import com.francescosalamone.model.state.DataState
import com.francescosalamone.pokemonapp.data.source.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): PokemonRepository {

    override fun getPokemonList(limit: UInt, offset: UInt): Flow<DataState<PokemonList>> {
        return flow {
            emit(DataState.Loading)
            val result = remoteDataSource.getPokemonList(limit, offset)

            if(result.isSuccessful) {
                result.body()?.let { emit(DataState.Success(it)) }
                    ?: run { emit(DataState.Failure(Exception("Missing body content.")))}
            } else {
                emit(DataState.Failure(Exception(result.errorBody()?.string())))
            }
        }
    }
}