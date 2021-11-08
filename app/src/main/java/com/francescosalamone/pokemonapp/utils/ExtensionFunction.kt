package com.francescosalamone.pokemonapp.utils

inline fun <reified T> Iterable<*>.filterIsInstanceOrNull(): List<T>? {
    return filterIsInstance<T>().let { if(it.isEmpty()) null else it }
}