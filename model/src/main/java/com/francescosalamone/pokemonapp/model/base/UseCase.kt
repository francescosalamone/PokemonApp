package com.francescosalamone.pokemonapp.model.base

import com.francescosalamone.pokemonapp.model.state.DataState
import kotlinx.coroutines.flow.Flow

interface UseCase<I, O> {

    fun invoke(params: I): Flow<DataState<O>>
}