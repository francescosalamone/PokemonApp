package com.francescosalamone.pokemonapp.di

import com.francescosalamone.pokemonapp.ui.dataFlow.PokemonDataFlow
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        AppDependency(get())
    }

    viewModel {
        PokemonDataFlow(get())
    }
}