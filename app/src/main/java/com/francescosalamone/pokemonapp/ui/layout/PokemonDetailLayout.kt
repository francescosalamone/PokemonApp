package com.francescosalamone.pokemonapp.ui.layout

import androidx.annotation.FloatRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.francescosalamone.pokemonapp.R
import com.francescosalamone.pokemonapp.model.dto.Pokemon
import com.francescosalamone.pokemonapp.ui.component.Chips
import com.francescosalamone.pokemonapp.ui.component.StatisticGraph
import com.francescosalamone.pokemonapp.ui.component.TopBar
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoilApi
@Composable
fun PokemonDetailLayout (
    pokemon: Pokemon,
    backButtonAction: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                title = pokemon.name?.capitalize(Locale.current) ?: stringResource(R.string.pokemon_detail_title),
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.back_action_content_description),
                onIconClick = backButtonAction
            )
        }
    ) {
        val maxImageSize = 250.dp
        val minImageSize = 100.dp
        val scrollState = rememberScrollState()

        val imageSize: Dp =
            (maxImageSize - scrollState.value.dp).coerceIn(minImageSize, maxImageSize)

        @FloatRange(from = 0.0, to = 1.0) val imageBias: Float =
            ((scrollState.value / 100f) + 0.5f).coerceIn(0.5f, 0.95f)

        if(scrollState.isScrollInProgress) {
            val result = when {
                (scrollState.value / 100f) + 0.5f > 1f -> 1f
                (scrollState.value / 100f) + 0.5f < 0.5f -> 0.5f
                else -> (scrollState.value / 100f) + 0.5f
            }

            Timber.d("bias $result for scroll ${scrollState.value}")
        }

        ConstraintLayout(
            modifier = Modifier
                .padding(start = 0.dp, top = imageSize / 2, bottom = 0.dp, end = 0.dp)
                .fillMaxSize()
        ) {
            val (card, image) = createRefs()
            val defaultColor = MaterialTheme.colors.surface
            val bgColor = remember { mutableStateOf(defaultColor) }

            val imagePainter = rememberImagePainter(
                data = pokemon.sprites?.other?.officialArtwork?.frontDefault,
                builder = {
                    fallback(R.drawable.ic_pokemon)
                    error(R.drawable.ic_pokemon)
                    crossfade(300)
                    allowHardware(false)
                }
            )
            val painterState: ImagePainter.State = imagePainter.state
            if(painterState is ImagePainter.State.Success) {
                LaunchedEffect(painterState) {
                    launch {
                        imagePainter.imageLoader.execute(imagePainter.request)
                            .drawable?.toBitmap()?.let {
                                val mutedColor = Palette
                                    .Builder(it)
                                    .generate()
                                    .getLightMutedColor(defaultColor.toArgb())

                                bgColor.value = Color(mutedColor)
                            }
                    }
                }
            }

            Card(
                backgroundColor = bgColor.value,
                shape = RoundedCornerShape(
                    topStart = 48.dp,
                    topEnd = 48.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                ),
                elevation = 6.dp,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .constrainAs(card) {
                        linkTo(
                            start = parent.start,
                            top = parent.top,
                            bottom = parent.bottom,
                            end = parent.end
                        )
                    }
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            top = imageSize / 2 + 8.dp,
                            bottom = 8.dp,
                            end = 8.dp
                        )
                        .fillMaxWidth()
                ) {
                    pokemon.types?.let { items ->
                        Chips(
                            items = items.mapNotNull { it.type?.name },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    StatisticGraph(
                        items = pokemon.stats,
                        modifier = Modifier
                            .size(300.dp)
                            .padding(20.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

            Image(
                painter = imagePainter,
                contentDescription = pokemon.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(imageSize)
                    .constrainAs(image) {
                        linkTo(
                            top = card.top,
                            bottom = card.top,
                            start = card.start,
                            end = card.end,
                            horizontalBias = imageBias
                        )
                    }
            )
        }
    }
}

@Preview
@Composable
fun DetailPreview() {
    PokemonDetailLayout(
        pokemon = Pokemon(
            name = "Pikachu",
            sprites = Pokemon.Sprites(
                other = Pokemon.Sprites.Other(
                    Pokemon.Sprites.Other.OfficialArtWork(
                        frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png"
                    )
                )
            ),
            types = listOf(
                Pokemon.TypeWrapper(
                    type = Pokemon.TypeWrapper.TypeDetail(
                        name = Pokemon.TypeWrapper.TypeDetail.Type.FIRE
                    )
                ),
                Pokemon.TypeWrapper(
                    type = Pokemon.TypeWrapper.TypeDetail(
                        name = Pokemon.TypeWrapper.TypeDetail.Type.GROUND
                    )
                ),
                Pokemon.TypeWrapper(
                    type = Pokemon.TypeWrapper.TypeDetail(
                        name = Pokemon.TypeWrapper.TypeDetail.Type.GRASS
                    )
                ),
                Pokemon.TypeWrapper(
                    type = Pokemon.TypeWrapper.TypeDetail(
                        name = Pokemon.TypeWrapper.TypeDetail.Type.WATER
                    )
                ),
                Pokemon.TypeWrapper(
                    type = Pokemon.TypeWrapper.TypeDetail(
                        name = Pokemon.TypeWrapper.TypeDetail.Type.DARK
                    )
                )
            ),
            stats = listOf(
                Pokemon.StatWrapper(
                    effort = 3,
                    baseStat = 15,
                    stat = Pokemon.StatWrapper.StatDetail(
                        type = Pokemon.StatWrapper.StatDetail.Stat.ACCURACY
                    )
                ),
                Pokemon.StatWrapper(
                    effort = 1,
                    baseStat = 25,
                    stat = Pokemon.StatWrapper.StatDetail(
                        type = Pokemon.StatWrapper.StatDetail.Stat.ATTACK
                    )
                )
            )
        ),
        backButtonAction = {}
    )
}