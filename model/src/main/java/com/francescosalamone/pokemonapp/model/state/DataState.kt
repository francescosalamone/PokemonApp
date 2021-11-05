package com.francescosalamone.pokemonapp.model.state

sealed class DataState<out T> {
    class Success<T>(val data: T): DataState<T>()
    object Loading: DataState<Nothing>()
    class Failure(val error: Exception): DataState<Nothing>()
}