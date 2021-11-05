package com.francescosalamone.pokemonapp

import android.app.Application
import com.francescosalamone.domain.di.domainModule
import com.francescosalamone.pokemonapp.data.di.apiModule
import org.koin.core.context.startKoin

class PokeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(domainModule, apiModule))
        }
    }
}