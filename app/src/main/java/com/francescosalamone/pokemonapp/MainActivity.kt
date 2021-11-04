package com.francescosalamone.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.francescosalamone.pokemonapp.data.model.Pokemon
import com.francescosalamone.pokemonapp.ui.layout.PokemonListLayout
import com.francescosalamone.pokemonapp.ui.theme.PokemonAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PokemonListLayout(
                        items = listOf(
                            Pokemon(
                                name = "Pickachu"
                            ),
                            Pokemon(
                                name = "bulbasaur"
                            ),
                            Pokemon(
                                name = "wobbuffet"
                            ),
                            Pokemon(
                                name = "forretress"
                            ),
                            Pokemon(
                                name = "dunsparce"
                            ),
                            Pokemon(
                                name = "gligar"
                            ),
                            Pokemon(
                                name = "snubbull"
                            ),
                            Pokemon(
                                name = "granbull"
                            )
                        )
                    )
                }
            }
        }
    }
}