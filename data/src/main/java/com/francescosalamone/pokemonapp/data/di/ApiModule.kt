package com.francescosalamone.pokemonapp.data.di

import com.francescosalamone.pokemonapp.data.api.PokemonService
import com.francescosalamone.pokemonapp.data.converters.PokemonConverter
import com.francescosalamone.pokemonapp.data.database.PokemonDb
import com.francescosalamone.pokemonapp.data.repository.PokemonRepository
import com.francescosalamone.pokemonapp.data.repository.PokemonRepositoryImpl
import com.francescosalamone.pokemonapp.data.source.RemoteDataSource
import com.francescosalamone.pokemonapp.data.source.RoomDataSource
import com.squareup.moshi.Moshi
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


var apiModule = module {
    single(qualifier = named("baseUrl")) { "https://pokeapi.co/api/v2/" }

    single {
        Moshi.Builder().build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(get<String>(qualifier = named("baseUrl")))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single {
        get<Retrofit>().create(PokemonService::class.java)
    }

    single {
        PokemonConverter(get())
    }

    single {
        PokemonDb.getPokemonDb(get(), get())
    }

    single {
        get<PokemonDb>().pokemonDao()
    }

    single {
        RemoteDataSource(get())
    }

    single {
        RoomDataSource(get())
    }

    single {
        PokemonRepositoryImpl(get(), get())
    } bind PokemonRepository::class
}