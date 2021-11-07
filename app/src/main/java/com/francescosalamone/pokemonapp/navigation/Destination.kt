package com.francescosalamone.pokemonapp.navigation

sealed class Destination {

    object List: Destination()
    object Detail: Destination()
}