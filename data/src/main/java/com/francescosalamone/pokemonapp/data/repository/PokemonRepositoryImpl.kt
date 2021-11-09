package com.francescosalamone.pokemonapp.data.repository

import com.francescosalamone.pokemonapp.data.exception.MissingCachedDataException
import com.francescosalamone.pokemonapp.data.source.DataSource
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import com.francescosalamone.pokemonapp.model.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class PokemonRepositoryImpl(
    private val remoteDataSource: DataSource,
    private val roomDataSource: DataSource
): PokemonRepository {

    override fun getPokemonList(limit: Int, offset: Int): Flow<DataState<PokemonList>> {
        Timber.d("Calling service pokemon?limit=$limit&offset=$offset")
        return callService(
            service = {
                remoteDataSource.getPokemonList(limit, offset)
            },
            dao = {
                roomDataSource.getPokemonList(limit, offset)
            },
            updateCache = {
                roomDataSource.savePokemonList(it)
            }
        )
    }

    override fun getPokemon(name: String): Flow<DataState<Pokemon>> {
        Timber.d("Calling service pokemon/$name")
        return callService(
            service = {
                remoteDataSource.getPokemon(name)
            },
            dao = {
                roomDataSource.getPokemon(name)
            },
            updateCache = {
                roomDataSource.savePokemon(it)
            }
        )
    }

    private fun <O> callService(
        service: suspend () -> O,
        dao: suspend () -> O,
        updateCache: suspend (O) -> Unit
    ): Flow<DataState<O>> {
        return flow {
            emit(DataState.Loading)

            try {
                val cachedResult = dao.invoke()
                Timber.d("Getting Cached data")
                emit(DataState.Success(cachedResult))

            } catch (exception: MissingCachedDataException) {

                Timber.d("Getting data from Network")
                val networkResult = service.invoke()

                Timber.d("Update pokemon in cache")
                updateCache.invoke(networkResult)

                emit(DataState.Success(networkResult))
            } catch (exception: Exception) {
                emit(DataState.Failure(exception))
            }
        }
    }
}