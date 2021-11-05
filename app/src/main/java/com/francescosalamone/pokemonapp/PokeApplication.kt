package com.francescosalamone.pokemonapp

import android.app.Application
import com.francescosalamone.pokemonapp.di.apiModule
import org.koin.core.context.startKoin

class PokeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(apiModule))
        }
    }
}