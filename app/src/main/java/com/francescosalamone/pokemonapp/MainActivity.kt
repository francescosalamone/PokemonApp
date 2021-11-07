package com.francescosalamone.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.francescosalamone.pokemonapp.di.appModule
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import com.francescosalamone.pokemonapp.navigation.Destination
import com.francescosalamone.pokemonapp.navigation.Navigator
import com.francescosalamone.pokemonapp.ui.contract.PokemonState
import com.francescosalamone.pokemonapp.ui.dataFlow.PokemonVm
import com.francescosalamone.pokemonapp.ui.layout.PokemonDetailLayout
import com.francescosalamone.pokemonapp.ui.layout.PokemonListLayout
import com.francescosalamone.pokemonapp.ui.theme.PokemonAppTheme
import io.uniflow.android.livedata.onStates
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber

class MainActivity : ComponentActivity() {
    val pokemonDataFlow: PokemonVm by viewModel()
    val navigator: Navigator<Destination> by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(appModule)
        startNavigation()

        onStates(pokemonDataFlow) { state ->
            when(state) {
                is PokemonState.Init -> pokemonDataFlow.fetchPokemons()

                is PokemonState.Loading -> {
                    updateUi(
                        isLoading = true,
                        pokemons = state.pokemons,
                        onNeedToFetch = pokemonDataFlow::fetchPokemons,
                        initialScrollPosition = pokemonDataFlow.scrollPosition,
                        scrollSaver = { pokemonDataFlow.scrollPosition = it }
                    )
                }

                is PokemonState.PokemonResult -> navigator.navigate(Destination.List){
                    updateUi(
                        pokemons = state.pokemons,
                        onNeedToFetch = pokemonDataFlow::fetchPokemons,
                        initialScrollPosition = pokemonDataFlow.scrollPosition,
                        scrollSaver = { pokemonDataFlow.scrollPosition = it }
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

    private fun updateUi(
        isLoading: Boolean = false,
        pokemons: List<PokemonList.PokemonData>,
        onNeedToFetch: () -> Unit,
        initialScrollPosition: Int = 0,
        scrollSaver: (Int) -> Unit
    ) {
        Timber.d("Returned pokemon list with ${pokemons.size} items")
        setContent {
            PokemonAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PokemonListLayout(
                        items = pokemons,
                        onItemClick = {
                            navigator.navigate(Destination.Detail) {
                                showDetail(it.name ?: "UNKNOWN")
                            }
                        },
                        onNeedToFetch = onNeedToFetch,
                        isLoading = isLoading,
                        initialScrollPosition = initialScrollPosition,
                        scrollSaver = scrollSaver
                    )
                }
            }
        }
    }

    private fun showDetail(name: String) {
        setContent {
            PokemonAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PokemonDetailLayout(name)
                }
            }
        }
    }

    private fun startNavigation() {
        Timber.d("Start navigation")
        navigator.navigate(Destination.List) {
            updateUi(
                pokemons = emptyList(),
                onNeedToFetch = pokemonDataFlow::fetchPokemons,
                scrollSaver = { pokemonDataFlow.scrollPosition = it }
            )
        }
    }

    override fun onBackPressed() {
        if(!navigator.back()) {
            super.onBackPressed()
        }
    }
}