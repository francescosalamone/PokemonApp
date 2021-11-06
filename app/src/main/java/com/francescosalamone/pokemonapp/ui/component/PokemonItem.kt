package com.francescosalamone.pokemonapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.francescosalamone.pokemonapp.R
import com.francescosalamone.pokemonapp.model.dto.PokemonList.PokemonData
import kotlinx.coroutines.launch

@ExperimentalCoilApi
@Composable
fun PokemonItem(
    scope: RowScope,
    pokemon: PokemonData,
    onClick: (pokemon: PokemonData) -> Unit
) {
    scope.apply {
        val defaultColor = MaterialTheme.colors.surface
        val bgColor = remember { mutableStateOf(defaultColor) }

        Card(
            backgroundColor = bgColor.value,
            elevation = 6.dp,
            modifier = Modifier
                .weight(1F)
                .align(Alignment.Top)
                .padding(8.dp)
                .clickable { onClick.invoke(pokemon) }
        ) {

            ConstraintLayout {
                val (image, name) = createRefs()
                val imagePainter = rememberImagePainter(
                    data = pokemon.pictureUrl,
                    builder = {
                        fallback(R.drawable.ic_pokemon)
                        error(R.drawable.ic_pokemon)
                        crossfade(300)
                        allowHardware(false)
                    }
                )
                val painterState: ImagePainter.State = imagePainter.state

                Image(
                    painter = imagePainter,
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .constrainAs(image) {
                            linkTo(
                                top = parent.top,
                                bottom = parent.bottom,
                                start = parent.start,
                                end = parent.end
                            )
                        }
                        .fillMaxHeight()
                        .size(120.dp)
                )
                if(painterState is ImagePainter.State.Success) {
                    LaunchedEffect(painterState) {
                        launch {
                            imagePainter.imageLoader.execute(imagePainter.request)
                                .drawable?.toBitmap()?.let {
                                    bgColor.value = Color(
                                        Palette
                                            .Builder(it)
                                            .generate()
                                            .getLightMutedColor(defaultColor.toArgb())
                                    )
                                }
                        }
                    }
                }
                Text(
                    text = pokemon.name ?: "UNKNOWN",
                    modifier = Modifier.constrainAs(name) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun Preview() {
    Row {
        PokemonItem(scope = this, pokemon = PokemonData(name = "Pickachu"), onClick = {})
    }
}