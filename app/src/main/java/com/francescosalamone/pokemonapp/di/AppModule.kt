package com.francescosalamone.pokemonapp.di

import org.koin.dsl.module

val appModule = module {
    single {
        AppDependency(get())
    }
}