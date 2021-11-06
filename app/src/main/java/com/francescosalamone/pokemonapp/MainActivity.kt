package com.francescosalamone.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.francescosalamone.pokemonapp.di.appModule
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import com.francescosalamone.pokemonapp.ui.contract.PokemonState
import com.francescosalamone.pokemonapp.ui.dataFlow.PokemonDataFlow
import com.francescosalamone.pokemonapp.ui.layout.PokemonListLayout
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

        onStates(pokemonDataFlow) { state ->
            when(state) {
                is PokemonState.Init -> pokemonDataFlow.fetchPokemons()

                is PokemonState.Loading -> {
                    updateUi(
                        isLoading = true,
                        pokemons = state.pokemons,
                        onNeedToFetch = pokemonDataFlow::fetchPokemons
                    )
                }

                is PokemonState.PokemonResult -> {
                    updateUi(
                        pokemons = state.pokemons,
                        onNeedToFetch = pokemonDataFlow::fetchPokemons
                    )
                }

                is PokemonState.Failure -> showError(state.exception)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(appModule)
    }

    private fun showError(exception: Exception) {
        Timber.d("Failure from service: $exception")
    }

    private fun updateUi(isLoading: Boolean = false, pokemons: List<PokemonList.PokemonData>, onNeedToFetch: () -> Unit ) {
        Timber.d("Returned pokemon list with ${pokemons.size} items")
        setContent {
            PokemonAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PokemonListLayout(
                        items = pokemons,
                        onItemClick = {
                            Timber.d("${it.name} clicked!")
                        },
                        onNeedToFetch = onNeedToFetch,
                        isLoading = isLoading
                    )
                }
            }
        }
    }
}