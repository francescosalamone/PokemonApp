package com.francescosalamone.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.francescosalamone.pokemonapp.di.AppDependency
import com.francescosalamone.pokemonapp.di.appModule
import com.francescosalamone.pokemonapp.ui.theme.PokemonAppTheme
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.java.KoinJavaComponent.inject

class MainActivity : ComponentActivity() {
    private val dependency: AppDependency by inject(AppDependency::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(appModule)

        setContent {
            PokemonAppTheme {
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(appModule)
    }
}