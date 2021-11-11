package com.francescosalamone.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.francescosalamone.pokemonapp.di.appModule
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.model.dto.PokemonList
import com.francescosalamone.pokemonapp.navigation.Destination
import com.francescosalamone.pokemonapp.navigation.Navigator
import com.francescosalamone.pokemonapp.ui.contract.PokemonState
import com.francescosalamone.pokemonapp.ui.dataFlow.PokemonVm
import com.francescosalamone.pokemonapp.ui.layout.PokemonDetailLayout
import com.francescosalamone.pokemonapp.ui.layout.PokemonListLayout
import com.francescosalamone.pokemonapp.ui.theme.PokemonAppTheme
import com.francescosalamone.pokemonapp.utils.filterIsInstanceOrNull
import io.uniflow.android.livedata.onStates
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber

class MainActivity : ComponentActivity() {
    private val pokemonDataFlow: PokemonVm by viewModel()
    private val navigator: Navigator<Destination> by inject()

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

                is PokemonState.PokemonResult<*> -> {
                    ((state.data as? List<*>)?.filterIsInstanceOrNull<PokemonList.PokemonData>())?.let {
                        navigator.navigate(Destination.List) {
                            updateUi(
                                pokemons = it,
                                onNeedToFetch = pokemonDataFlow::fetchPokemons,
                                initialScrollPosition = pokemonDataFlow.scrollPosition,
                                scrollSaver = { pokemonDataFlow.scrollPosition = it }
                            )
                        }
                    } ?: (state.data as? Pokemon)?.let {
                        navigator.navigate(Destination.Detail) {
                            showDetail(it)
                        }
                    } ?: run {
                        Timber.e("Unknown type ${state.data?.javaClass}, navigation not possible")
                    }

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
        updateUi(
            isLoading = false,
            pokemons = pokemonDataFlow.pokemons,
            onNeedToFetch = pokemonDataFlow::fetchPokemons,
            initialScrollPosition = pokemonDataFlow.scrollPosition,
            scrollSaver = { pokemonDataFlow.scrollPosition = it }
        )
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
                            it.name?.let { name ->
                                pokemonDataFlow.getPokemonDetail(name)
                            } ?: run {
                                Timber.e("Impossible to load detail, name is null")
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

    private fun showDetail(pokemon: Pokemon) {
        setContent {
            PokemonAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PokemonDetailLayout(pokemon, ::onBackPressed)
                }
            }
        }
    }

    private fun startNavigation() {
        Timber.d("Start navigation")
        navigator.navigate(Destination.List) {
            updateUi(
                pokemons = pokemonDataFlow.pokemons,
                onNeedToFetch = pokemonDataFlow::fetchPokemons,
                scrollSaver = { pokemonDataFlow.scrollPosition = it },
                initialScrollPosition = pokemonDataFlow.scrollPosition
            )
        }
    }

    override fun onBackPressed() {
        if(!navigator.back()) {
            super.onBackPressed()
        }
    }
}