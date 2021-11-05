package com.francescosalamone.pokemonapp.di

import com.francescosalamone.pokemonapp.data.PokemonService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


var apiModule = module {
    single(qualifier = named("baseUrl")) { "https://pokeapi.co/api/v2/" }

    single {
        Retrofit.Builder()
            .baseUrl(get<String>(qualifier = named("baseUrl")))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(PokemonService::class.java)
    }
}