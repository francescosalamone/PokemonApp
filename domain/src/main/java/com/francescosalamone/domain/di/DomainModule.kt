package com.francescosalamone.domain.di

import com.francescosalamone.domain.usecase.FetchPokemonListUseCase
import org.koin.dsl.module

var domainModule = module {
    single { FetchPokemonListUseCase(get()) }
}