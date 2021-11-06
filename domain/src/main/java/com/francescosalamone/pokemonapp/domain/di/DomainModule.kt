package com.francescosalamone.pokemonapp.domain.di

import com.francescosalamone.pokemonapp.domain.usecase.FetchPokemonListUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

var domainModule = module {

    single(qualifier = named("limit")) {
        20u
    }

    single(qualifier = named("initialPage")) {
        0u
    }

    single {
        FetchPokemonListUseCase(
            repository = get(),
            initialPage = get(qualifier = named("initialPage")),
            limit = get(qualifier = named("limit"))
        )
    }
}