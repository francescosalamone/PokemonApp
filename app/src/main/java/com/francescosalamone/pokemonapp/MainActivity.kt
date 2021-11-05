package com.francescosalamone.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.francescosalamone.pokemonapp.di.appModule
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import com.francescosalamone.pokemonapp.ui.component.Loader
import com.francescosalamone.pokemonapp.ui.contract.PokemonState
import com.francescosalamone.pokemonapp.ui.dataFlow.PokemonDataFlow
import com.francescosalamone.pokemonapp.ui.theme.PokemonAppTheme
import io.uniflow.android.livedata.onStates
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber

class MainActivity : ComponentActivity() {
    val pokemonDataFlow: PokemonDataFlow by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        loadKoinModules(appModule)

        if(Timber.treeCount() == 0) {
            Timber.plant(Timber.DebugTree())
        }

        onStates(pokemonDataFlow) { state ->
            when(state) {
                is PokemonState.Init -> Timber.d("State initialized.")
                is PokemonState.Loading -> showLoading()
                is PokemonState.PokemonResult -> updateUi(state.pokemons)
                is PokemonState.Failure -> showError(state.exception)
            }
        }

        pokemonDataFlow.fetchPokemons()
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(appModule)
    }

    private fun showLoading() {
        setContent {
            PokemonAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Loader()
                }
            }
        }
    }

    private fun showError(exception: Exception) {
        Timber.d("Failure from service: $exception")
    }

    private fun updateUi(pokemons: PokemonList) {
        Timber.d("Returned pokemon list with ${pokemons.results.size} items")
        setContent {
            PokemonAppTheme {
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
    }
}