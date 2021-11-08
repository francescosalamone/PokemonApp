package com.francescosalamone.pokemonapp.ui.layout

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.francescosalamone.pokemonapp.R
import com.francescosalamone.pokemonapp.model.dto.PokemonList.PokemonData
import com.francescosalamone.pokemonapp.ui.component.Loader
import com.francescosalamone.pokemonapp.ui.component.PokemonItem
import com.francescosalamone.pokemonapp.ui.component.TopBar
import com.francescosalamone.pokemonapp.ui.theme.PokemonAppTheme
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun PokemonListLayout(
    items: List<PokemonData>,
    columns: Int = 2,
    hPadding: Int = 8,
    isLoading: Boolean = false,
    onItemClick: (PokemonData) -> Unit,
    onNeedToFetch: () -> Unit,
    initialScrollPosition: Int = 0,
    scrollSaver: (Int) -> Unit
) {
    val chunkedList = items.chunked(columns)
    val listState = rememberLazyListState()
    val scrollState = rememberScrollState()

    LaunchedEffect(scrollState) {
        launch {
            Timber.d("Initial scroll position $initialScrollPosition")
            listState.scrollToItem(initialScrollPosition)
        }
    }

    if(listState.isScrollInProgress) {
        Timber.d("Scrolling")
        Timber.d("Scroll position saved at ${listState.firstVisibleItemIndex}")
        scrollSaver.invoke(listState.firstVisibleItemIndex)
    }

    if (listState.layoutInfo.visibleItemsInfo.any { it.index == chunkedList.lastIndex } && !isLoading) {
        Timber.d("Need to fetch new data")
        onNeedToFetch.invoke()
    }

    Scaffold(
        topBar = {
            TopBar(title = stringResource(id = R.string.app_name))
        }
    ) {
        ConstraintLayout {
            val (content, progress) = createRefs()

            LazyColumn(
                modifier = Modifier
                    .constrainAs(content) {
                        linkTo(
                            top = parent.top,
                            bottom = parent.bottom,
                            end = parent.end,
                            start = parent.start
                        )
                    }
                    .padding(horizontal = hPadding.dp)
                    .fillMaxWidth()
                    .scrollable(scrollState, Orientation.Vertical),
                state = listState
            ) {
                items(chunkedList) { item ->
                    Row {
                        item.forEach { pokemon ->
                            PokemonItem(this, pokemon, onItemClick)
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

            if(isLoading) {
                Loader(
                    modifier = Modifier.constrainAs(progress) {
                        linkTo(top = parent.top, bottom = parent.bottom, end = parent.end, start = parent.start)
                    }
                )
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
                PokemonData(
                    uid = 1,
                    name = "Pikachu"
                ),
                PokemonData(
                    uid = 2,
                    name = "bulbasaur"
                ),
                PokemonData(
                    uid = 3,
                    name = "wobbuffet"
                ),
                PokemonData(
                    uid = 4,
                    name = "forretress"
                ),
                PokemonData(
                    uid = 5,
                    name = "dunsparce"
                ),
                PokemonData(
                    uid = 6,
                    name = "gligar"
                ),
                PokemonData(
                    uid = 7,
                    name = "snubbull"
                ),
                PokemonData(
                    uid = 8,
                    name = "granbull"
                )
            ),
            onItemClick = {},
            onNeedToFetch = {},
            scrollSaver = {}
        )
    }
}