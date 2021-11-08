package com.francescosalamone.pokemonapp.domain.di

import com.francescosalamone.pokemonapp.domain.usecase.FetchPokemonListUseCase
import com.francescosalamone.pokemonapp.domain.usecase.PokemonDetailUseCase
import com.francescosalamone.pokemonapp.domain.utils.provideUseCase
import com.francescosalamone.pokemonapp.model.base.UseCase
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

var domainModule = module {

    single(qualifier = named("limit")) {
        20
    }

    single(qualifier = named("initialPage")) {
        0
    }

    provideUseCase(
        qualifier = named("listUseCase")
    ) {
        FetchPokemonListUseCase(
            repository = get(),
            initialPage = get(qualifier = named("initialPage")),
            limit = get(qualifier = named("limit"))
        )
    } bind UseCase::class

    provideUseCase(
        qualifier = named("detailUseCase")
    ) {
        PokemonDetailUseCase(get())
    } bind UseCase::class
}