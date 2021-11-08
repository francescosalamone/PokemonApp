package com.francescosalamone.pokemonapp.di

import com.francescosalamone.pokemonapp.navigation.Destination
import com.francescosalamone.pokemonapp.navigation.Navigator
import com.francescosalamone.pokemonapp.ui.dataFlow.PokemonDataFlow
import com.francescosalamone.pokemonapp.ui.dataFlow.PokemonVm
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {

    viewModel {
        PokemonDataFlow(get(named("listUseCase")), get(named("detailUseCase")))
    } bind PokemonVm::class

    single {
        Navigator<Destination>()
    }
}