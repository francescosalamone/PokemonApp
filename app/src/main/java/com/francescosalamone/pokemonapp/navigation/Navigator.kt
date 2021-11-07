package com.francescosalamone.pokemonapp.navigation

import timber.log.Timber

class Navigator<T>() {

    private val backStack: LinkedHashMap<T, () -> Unit> = linkedMapOf()

    fun back(): Boolean {
        return if(canNavigateBack()) {
            backStack.remove(backStack.keys.last())
            backStack.values.last().invoke()
            Timber.d("Back handled")
            true
        } else {
            Timber.d("Back not handled")
            false
        }
    }

    fun navigate(destination: T, action: () -> Unit) {
        backStack[destination] = action
        action.invoke()
    }

    private fun canNavigateBack() = backStack.size > 1
}