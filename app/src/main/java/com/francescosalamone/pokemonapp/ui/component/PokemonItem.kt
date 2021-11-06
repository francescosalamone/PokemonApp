package com.francescosalamone.pokemonapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.francescosalamone.pokemonapp.R
import com.francescosalamone.pokemonapp.model.dto.PokemonList.PokemonData

@Composable
fun PokemonItem(
    scope: RowScope,
    pokemon: PokemonData,
    onClick: (pokemon: PokemonData) -> Unit
) {
    scope.apply {
        Card(
            elevation = 6.dp,
            modifier = Modifier
                .weight(1F)
                .align(Alignment.Top)
                .padding(8.dp)
                .clickable { onClick.invoke(pokemon) }
        ) {

            ConstraintLayout {
                val (image, name) = createRefs()

                Image(
                    painter = rememberImagePainter(
                        data = pokemon.pictureUrl,
                        builder = {
                            fallback(R.drawable.ic_pokemon)
                            error(R.drawable.ic_pokemon)
                            crossfade(300)
                        }
                    ),
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