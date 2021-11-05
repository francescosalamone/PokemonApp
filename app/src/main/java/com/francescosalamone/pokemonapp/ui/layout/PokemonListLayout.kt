package com.francescosalamone.pokemonapp.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.francescosalamone.pokemonapp.R
import com.francescosalamone.pokemonapp.data.dto.Pokemon
import com.francescosalamone.pokemonapp.ui.component.PokemonItem
import com.francescosalamone.pokemonapp.ui.theme.PokemonAppTheme

@Composable
fun PokemonListLayout(
    items: List<Pokemon>,
    columns: Int = 3,
    hPadding: Int = 8
) {
    val chunkedList = items.chunked(columns)
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = hPadding.dp)
                .fillMaxWidth(),
            state = listState
        ) {
            items(chunkedList) { item ->
                Row {
                    item.forEach { pokemon ->
                        PokemonItem(this, pokemon)
                    }

                    repeat(columns - item.size) {
                        Box(
                            modifier = Modifier
                                .weight(1F)
                                .padding(8.dp)
                        ) {}
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokemonAppTheme {
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