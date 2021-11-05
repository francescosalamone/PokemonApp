package com.francescosalamone.pokemonapp.domain.di

import com.francescosalamone.pokemonapp.domain.usecase.FetchPokemonListUseCase
import org.koin.dsl.module

var domainModule = module {
    single { FetchPokemonListUseCase(get()) }
}