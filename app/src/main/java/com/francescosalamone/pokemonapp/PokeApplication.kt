package com.francescosalamone.pokemonapp

import android.app.Application
import com.francescosalamone.pokemonapp.data.di.apiModule
import com.francescosalamone.pokemonapp.domain.di.domainModule
import org.koin.core.context.startKoin
import timber.log.Timber

class PokeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(domainModule, apiModule))
        }

        if(Timber.treeCount() == 0) {
            Timber.plant(Timber.DebugTree())
        }
    }
}