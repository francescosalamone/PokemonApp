package com.francescosalamone.pokemonapp.domain.di

import com.francescosalamone.pokemonapp.domain.usecase.FetchPokemonListUseCase
import com.francescosalamone.pokemonapp.domain.usecase.PokemonDetailUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

var domainModule = module {

    single(qualifier = named("limit")) {
        20
    }

    single(qualifier = named("initialPage")) {
        0
    }

    single {
        FetchPokemonListUseCase(
            repository = get(),
            initialPage = get(qualifier = named("initialPage")),
            limit = get(qualifier = named("limit"))
        )
    }

    single {
        PokemonDetailUseCase(get())
    }
}