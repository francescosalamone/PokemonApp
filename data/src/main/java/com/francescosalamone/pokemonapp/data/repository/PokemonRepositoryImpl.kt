package com.francescosalamone.pokemonapp.data.repository

import com.francescosalamone.pokemonapp.data.source.RemoteDataSource
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import com.francescosalamone.pokemonapp.model.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import timber.log.Timber

class PokemonRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): PokemonRepository {

    override fun getPokemonList(limit: UInt, offset: UInt): Flow<DataState<PokemonList>> {
        return callService {
            Timber.d("Calling service pokemon?limit=$limit&offset=$offset")
            remoteDataSource.getPokemonList(limit, offset)
        }
    }

    override fun getPokemon(name: String): Flow<DataState<Pokemon>> {
        return callService {
            Timber.d("Calling service pokemon/$name")
            remoteDataSource.getPokemon(name)
        }
    }

    private fun <O> callService(service: suspend () -> Response<O>): Flow<DataState<O>> {
        return flow {
            emit(DataState.Loading)

            val result = service.invoke()

            if(result.isSuccessful) {
                result.body()?.let {
                    Timber.d("RESPONSE NETWORK: $it")
                    emit(DataState.Success(it))
                } ?: run {
                        Timber.e("RESPONSE NETWORK: Missing body content.")
                        emit(DataState.Failure(Exception("Missing body content.")))
                    }
            } else {
                val error = result.errorBody()?.string()
                Timber.e("RESPONSE NETWORK: $error")
                emit(DataState.Failure(Exception(error)))
            }
        }
    }
}