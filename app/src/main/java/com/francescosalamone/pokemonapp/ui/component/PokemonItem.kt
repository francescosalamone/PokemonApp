package com.francescosalamone.pokemonapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.francescosalamone.pokemonapp.R
import com.francescosalamone.pokemonapp.data.model.Pokemon

@Composable
fun PokemonItem(
    scope: RowScope,
    pokemon: Pokemon
) {
    scope.apply {
        Card(
            elevation = 6.dp,
            modifier = Modifier
                .weight(1F)
                .align(Alignment.Top)
                .padding(8.dp)
        ) {

            ConstraintLayout {
                val (image, name) = createRefs()

                Image(
                    painter = painterResource(
                        id = R.drawable.ic_launcher_background
                    ),
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.constrainAs(image) {
                        linkTo(
                            top = parent.top,
                            bottom = parent.bottom,
                            start = parent.start,
                            end = parent.end
                        )
                    }
                        .fillMaxWidth()
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
        PokemonItem(scope = this, pokemon = Pokemon(name = "Pickachu"))
    }
}