package com.francescosalamone.pokemonapp.data.di

import com.squareup.moshi.Moshi
import org.koin.dsl.module

var dataModuleTest = module {
    single {
        Moshi.Builder().build()
    }
}